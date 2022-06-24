#include "com_lc_Executor.h"
#include "runner.h"

void setClassField(JNIEnv *jenv, jobject obj, jclass jclazz, char *name, int val) {
    // jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, name, "Ljava/lang/Integer");
    jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, name, "I");
    (*jenv)->SetIntField(jenv, obj, fieldId, val);
}
jobject generateResult(JNIEnv *jenv, int cpuTime, int signal, int memory, int exitCode, int result, int error, int realTime) {
    jclass resultClass = (*jenv)->FindClass(jenv, "com/lc/Result");
    jmethodID constructorId = (*jenv)->GetMethodID(jenv, resultClass, "<init>", "()V");
    jobject resultObj = (*jenv)->NewObject(jenv, resultClass, constructorId);

    setClassField(jenv, resultObj, resultClass, "cpuTime", cpuTime);
    setClassField(jenv, resultObj, resultClass, "signal", signal);
    setClassField(jenv, resultObj, resultClass, "memory", memory);
    setClassField(jenv, resultObj, resultClass, "exitCode", exitCode);
    setClassField(jenv, resultObj, resultClass, "result", result);
    setClassField(jenv, resultObj, resultClass, "error", error);
    setClassField(jenv, resultObj, resultClass, "realTime", realTime);

    return resultObj;
}

jint getIntFromObj(JNIEnv *jenv, jobject obj, char *fieldName) {
    jclass jclazz = (*jenv)->GetObjectClass(jenv, obj);
    jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, fieldName, "I");
    return (*jenv)->GetIntField(jenv, obj, fieldId);
}

const char *getStringFromObj(JNIEnv *jenv, jobject obj, char *fieldName) {
    jclass jclazz = (*jenv)->GetObjectClass(jenv, obj);
    jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, fieldName, "Ljava/lang/String;");
    jstring str = (jstring)(*jenv)->GetObjectField(jenv, obj, fieldId);
    if (str == NULL) {
        return NULL;
    }
    return (*jenv)->GetStringUTFChars(jenv, str, NULL);
}

jarray getOjbectArray(JNIEnv *jenv, jobject obj, char *fieldName) {
    jclass jclazz = (*jenv)->GetObjectClass(jenv, obj);
    jfieldID fieldId = (*jenv)->GetFieldID(jenv, jclazz, fieldName, "[Ljava/lang/String;");
    jobject object = (jobject)(*jenv)->GetObjectField(jenv, obj, fieldId);
    if (object == NULL) {
        return NULL;
    }
    jarray array = (jarray)object;
    return array;
}

void processStringArray(JNIEnv *jenv, jobject obj, char *param[255], char *name) {
    jarray array = getOjbectArray(jenv, obj, name);
    if (array == NULL) {
        return;
    }
    jsize n = (*jenv)->GetArrayLength(jenv, array);
    int i = 1;
    while (i <= n) {
        jstring s = (jstring)(*jenv)->GetObjectArrayElement(jenv, array, i - 1);
        param[i] = (*jenv)->GetStringUTFChars(jenv, s, NULL);
        i++;
    }
    param[i] = NULL;
}

int *max_cpu_time, *max_real_time, *max_memory, *max_stack, *memory_limit_check_only,
    *max_process_number, *max_output_size, *uid, *gid;
char *exe_path, *input_path, *output_path, *error_path, *args, *env, *log_path, *seccomp_rule_name;

JNIEXPORT jobject JNICALL Java_com_lc_Executor_run(JNIEnv *jenv, jclass jclazz, jobject obj) {

    struct config _config;
    struct result _result = {0, 0, 0, 0, 0, 0, 0};

    int maxCpuTime = getIntFromObj(jenv, obj, "maxCpuTime");
    if (maxCpuTime) {
        _config.max_cpu_time = maxCpuTime;
    } else {
        _config.max_cpu_time = UNLIMITED;
    }

    int maxRealTime = getIntFromObj(jenv, obj, "maxRealTime");
    if (maxRealTime) {
        _config.max_real_time = maxRealTime;
    } else {
        _config.max_real_time = UNLIMITED;
    }

    int maxMemory = getIntFromObj(jenv, obj, "maxMemory");
    if (maxMemory) {
        _config.max_memory = (long)maxMemory;
    } else {
        _config.max_memory = UNLIMITED;
    }

    _config.memory_limit_check_only = 0;

    int maxStack = getIntFromObj(jenv, obj, "maxStack");
    if (maxStack) {
        _config.max_stack = (long)maxStack;
    } else {
        _config.max_stack = 16 * 1024 * 1024;
    }

    int maxProcessNumber = getIntFromObj(jenv, obj, "maxProcessNumber");
    if (maxProcessNumber) {
        _config.max_process_number = maxProcessNumber;
    } else {
        _config.max_process_number = UNLIMITED;
    }

    int maxOutputSize = getIntFromObj(jenv, obj, "maxOutputSize");
    if (maxOutputSize) {
        _config.max_output_size = (long)maxOutputSize;
    } else {
        _config.max_output_size = UNLIMITED;
    }

    _config.exe_path = getStringFromObj(jenv, obj, "exePath");

    char *inputPath = getStringFromObj(jenv, obj, "inputPath");
    if (inputPath) {
        _config.input_path = inputPath;
    } else {
        _config.input_path = "/dev/stdin";
    }

    char *outputPath = getStringFromObj(jenv, obj, "outputPath");
    if (outputPath) {
        _config.output_path = outputPath;
    } else {
        _config.output_path = "/dev/stdout";
    }

    char *errorPath = getStringFromObj(jenv, obj, "errorPath");
    if (errorPath) {
        _config.error_path = errorPath;
    } else {
        _config.error_path = "/dev/stderr";
    }

    _config.args[0] = _config.exe_path;
    // set args
    processStringArray(jenv, obj, _config.args, "args");
    // set env
    processStringArray(jenv, obj, _config.args, "env");

    char *logPath = getStringFromObj(jenv, obj, "logPath");
    if (logPath) {
        _config.log_path = logPath;
    } else {
        _config.log_path = "judger.log";
    }

    char *seccompRuleName = getStringFromObj(jenv, obj, "seccompRuleName");
    if (seccompRuleName) {
        _config.seccomp_rule_name = seccompRuleName;
    } else {
        _config.seccomp_rule_name = NULL;
    }

    int uidd = getIntFromObj(jenv, obj, "uid");
    if (uidd) {
        _config.uid = (uid_t)uidd;
    } else {
        _config.uid = 65534;
    }

    int gidd = getIntFromObj(jenv, obj, "gid");
    if (gidd) {
        _config.gid = (gid_t)gidd;
    } else {
        _config.gid = 65534;
    }

    run(&_config, &_result);

    /**printf("{\n"
           "    \"cpu_time\": %d,\n"
           "    \"real_time\": %d,\n"
           "    \"memory\": %ld,\n"
           "    \"signal\": %d,\n"
           "    \"exit_code\": %d,\n"
           "    \"error\": %d,\n"
           "    \"result\": %d\n"
           "}",
           _result.cpu_time,
           _result.real_time,
           _result.memory,
           _result.signal,
           _result.exit_code,
           _result.error,
           _result.result);
    **/

    return generateResult(jenv,
                          _result.cpu_time,
                          _result.signal,
                          _result.memory,
                          _result.exit_code,
                          _result.result,
                          _result.error,
                          _result.real_time);
}