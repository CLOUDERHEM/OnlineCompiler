#include "io_github_clouderhem_executor_Executor.h"
#include "runner.h"
#include <string.h>

void setFieldID(JNIEnv* jenv, jobject obj, jclass jclazz, char* name, int val) {
    // jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, name, "Ljava/lang/Integer");
    jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, name, "I");
    (*jenv)->SetIntField(jenv, obj, fieldId, val);
}

jobject generateResult(JNIEnv* jenv, int cpuTime, int signal, int memory, int exitCode, int result, int error, int realTime) {
    jclass resultClass = (*jenv)->FindClass(jenv, "io/github/clouderhem/executor/Result");
    jmethodID constructorId = (*jenv)->GetMethodID(jenv, resultClass, "<init>", "()V");
    jobject resultObj = (*jenv)->NewObject(jenv, resultClass, constructorId);

    setFieldID(jenv, resultObj, resultClass, "cpuTime", cpuTime);
    setFieldID(jenv, resultObj, resultClass, "signal", signal);
    setFieldID(jenv, resultObj, resultClass, "memory", memory);
    setFieldID(jenv, resultObj, resultClass, "exitCode", exitCode);
    setFieldID(jenv, resultObj, resultClass, "result", result);
    setFieldID(jenv, resultObj, resultClass, "error", error);
    setFieldID(jenv, resultObj, resultClass, "realTime", realTime);

    return resultObj;
}

jint getJintFromObj(JNIEnv* jenv, jobject obj, char* fieldName) {
    jclass jclazz = (*jenv)->GetObjectClass(jenv, obj);
    jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, fieldName, "I");
    return (*jenv)->GetIntField(jenv, obj, fieldId);
}

const char* getCharArrayFromObj(JNIEnv* jenv, jobject obj, char* fieldName) {
    jclass jclazz = (*jenv)->GetObjectClass(jenv, obj);
    jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, fieldName, "Ljava/lang/String;");
    jstring str = (jstring)(*jenv)->GetObjectField(jenv, obj, fieldId);
    if (str == NULL) {
        return NULL;
    }
    return (*jenv)->GetStringUTFChars(jenv, str, NULL);
}

jarray getJarrayFromObj(JNIEnv* jenv, jobject obj, char* fieldName) {
    jclass jclazz = (*jenv)->GetObjectClass(jenv, obj);
    jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, fieldName, "[Ljava/lang/String;");
    jobject object = (jobject)(*jenv)->GetObjectField(jenv, obj, fieldId);
    if (object == NULL) {
        return NULL;
    }
    return (jarray)object;
}

void fillParam(JNIEnv* jenv, jobject obj, char* param[255], char* name) {
    jarray array = getJarrayFromObj(jenv, obj, name);
    if (array == NULL) {
        return;
    }
    jsize n = (*jenv)->GetArrayLength(jenv, array);
    int i = 1;

    while (i < n) {
        param[i] = NULL;
        jstring s = (jstring)(*jenv)->GetObjectArrayElement(jenv, array, i);
        if (!strcmp(name, "env")) {
            param[i - 1] = (*jenv)->GetStringUTFChars(jenv, s, NULL);
        }
        else {
            param[i] = (*jenv)->GetStringUTFChars(jenv, s, NULL);
        }

        i++;
    }
    param[i] = NULL;
}

JNIEXPORT jobject JNICALL Java_io_github_clouderhem_executor_Executor_run(JNIEnv* jenv, jclass jclazz, jobject obj) {

    struct config _config;
    struct result _result = { 0, 0, 0, 0, 0, 0, 0 };

    int maxCpuTime = getJintFromObj(jenv, obj, "maxCpuTime");
    if (maxCpuTime) {
        _config.max_cpu_time = maxCpuTime;
    }
    else {
        _config.max_cpu_time = UNLIMITED;
    }

    int maxRealTime = getJintFromObj(jenv, obj, "maxRealTime");
    if (maxRealTime) {
        _config.max_real_time = maxRealTime;
    }
    else {
        _config.max_real_time = UNLIMITED;
    }

    int maxMemory = getJintFromObj(jenv, obj, "maxMemory");
    if (maxMemory) {
        _config.max_memory = (long)maxMemory;
    }
    else {
        _config.max_memory = UNLIMITED;
    }

    _config.memory_limit_check_only = 0;

    int maxStack = getJintFromObj(jenv, obj, "maxStack");
    if (maxStack) {
        _config.max_stack = (long)maxStack;
    }
    else {
        _config.max_stack = 16 * 1024 * 1024;
    }

    int maxProcessNumber = getJintFromObj(jenv, obj, "maxProcessNumber");
    if (maxProcessNumber) {
        _config.max_process_number = maxProcessNumber;
    }
    else {
        _config.max_process_number = UNLIMITED;
    }

    int maxOutputSize = getJintFromObj(jenv, obj, "maxOutputSize");
    if (maxOutputSize) {
        _config.max_output_size = (long)maxOutputSize;
    }
    else {
        _config.max_output_size = UNLIMITED;
    }

    _config.exe_path = getCharArrayFromObj(jenv, obj, "exePath");

    char* inputPath = getCharArrayFromObj(jenv, obj, "inputPath");
    if (inputPath) {
        _config.input_path = inputPath;
    }
    else {
        _config.input_path = "/dev/stdin";
    }

    char* outputPath = getCharArrayFromObj(jenv, obj, "outputPath");
    if (outputPath) {
        _config.output_path = outputPath;
    }
    else {
        _config.output_path = "/dev/stdout";
    }

    char* errorPath = getCharArrayFromObj(jenv, obj, "errorPath");
    if (errorPath) {
        _config.error_path = errorPath;
    }
    else {
        _config.error_path = "/dev/stderr";
    }

    _config.args[0] = _config.exe_path;
    // set args
    fillParam(jenv, obj, _config.args, "args");
    // set env
    fillParam(jenv, obj, _config.env, "env");

    char* logPath = getCharArrayFromObj(jenv, obj, "logPath");
    if (logPath) {
        _config.log_path = logPath;
    }
    else {
        _config.log_path = "judger.log";
    }

    char* seccompRuleName = getCharArrayFromObj(jenv, obj, "seccompRuleName");
    if (seccompRuleName) {
        _config.seccomp_rule_name = seccompRuleName;
    }
    else {
        _config.seccomp_rule_name = NULL;
    }

    int uidd = getJintFromObj(jenv, obj, "uid");
    if (uidd != -1) {
        _config.uid = (uid_t)uidd;
    }
    else {
        _config.uid = 65534;
    }

    int gidd = getJintFromObj(jenv, obj, "gid");
    if (gidd != -1) {
        _config.gid = (gid_t)gidd;
    }
    else {
        _config.gid = 65534;
    }

    run(&_config, &_result);

    return generateResult(jenv,
        _result.cpu_time,
        _result.signal,
        _result.memory,
        _result.exit_code,
        _result.result,
        _result.error,
        _result.real_time);

}