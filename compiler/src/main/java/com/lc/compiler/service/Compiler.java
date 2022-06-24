package com.lc.compiler.service;

import cn.hutool.core.io.FileUtil;
import com.lc.Executor;
import com.lc.Params;
import com.lc.Result;
import com.lc.compiler.config.SystemConfig;
import com.lc.compiler.config.language.LanguageConfig;
import com.lc.compiler.model.CompileResult;
import org.springframework.beans.BeanUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 2:05 PM
 */

public class Compiler {
    public static CompileResult compile(LanguageConfig languageConfig, String srcPath, String outputDir) {
        String exePath = outputDir + languageConfig.exeName();
        String compileOutPath = outputDir + "compile.log";

        String[] commands = languageConfig.compileCommand().
                replace("{srcPath}", srcPath).
                replace("{exeDir}", outputDir).
                replace("{exePath}", exePath).split(" ");

        Params compileParams = new Params();
        compileParams.setMaxCpuTime(languageConfig.maxCpuTime());
        compileParams.setMaxRealTime(languageConfig.maxRealTime());
        compileParams.setMaxMemory(languageConfig.maxMemory());
        compileParams.setMaxProcessNumber(LanguageConfig.MAX_PROCESS_NUMBER);
        compileParams.setMaxStack(LanguageConfig.MAX_STACK);
        compileParams.setMaxOutputSize(LanguageConfig.MAX_OUTPUT_SIZE);
        // eg: gcc
        compileParams.setExePath(commands[0]);
        compileParams.setInputPath(srcPath);
        compileParams.setErrorPath(compileOutPath);
        compileParams.setOutputPath(compileOutPath);
        compileParams.setLogPath(compileOutPath);
        compileParams.setSeccompRuleName(null);
        // eg gcc(exePath) -o main main.c
        compileParams.setArgs(commands);

        compileParams.setUid(SystemConfig.UID);
        compileParams.setGid(SystemConfig.GID);

        Result run = Executor.exec(compileParams);

        CompileResult compileResult = new CompileResult();
        compileResult.setStatus(0);
        if (run == null) {
            compileResult.setStatus(3);
            compileResult.setCompileMsg("编译时未知错误, 请反馈给开发者");
            return compileResult;
        }
        BeanUtils.copyProperties(run, compileResult);

        if (run.getResult() != 0 && FileUtil.exist(compileOutPath)) {
            compileResult.setStatus(1);
            compileResult.setCompileMsg(FileUtil.readString(compileOutPath, StandardCharsets.UTF_8));
        }

        compileResult.setExePath(exePath);

        return compileResult;

    }
}
