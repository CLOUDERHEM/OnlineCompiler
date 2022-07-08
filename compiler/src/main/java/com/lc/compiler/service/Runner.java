package com.lc.compiler.service;

import cn.hutool.core.io.FileUtil;
import com.lc.Executor;
import com.lc.Params;
import com.lc.Result;
import com.lc.compiler.config.SystemConfig;
import com.lc.compiler.config.language.LanguageConfig;
import com.lc.compiler.model.RunResult;
import org.springframework.beans.BeanUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 2:29 PM
 */
public class Runner {
    public static RunResult run(LanguageConfig languageConfig, String exePath, String inputPath, String outputPath, String outputDir) {

        String[] runCommands = languageConfig.command().
                // only running java has this
                replace("{exeDir}", outputDir).
                replace("{maxMemory}", String.valueOf(languageConfig.maxMemory())).
                replace("{exePath}", exePath).
                split(" ");

        Params runParams = new Params();
        runParams.setMaxCpuTime(languageConfig.maxCpuTime());
        runParams.setMaxRealTime(languageConfig.maxRealTime());
        runParams.setMaxMemory(languageConfig.maxMemory());
        runParams.setMaxProcessNumber(LanguageConfig.MAX_PROCESS_NUMBER);
        runParams.setMaxStack(LanguageConfig.MAX_STACK);
        runParams.setMaxOutputSize(LanguageConfig.MAX_OUTPUT_SIZE);
        runParams.setExePath(runCommands[0]);
        runParams.setInputPath(inputPath);
        runParams.setOutputPath(outputPath);
        runParams.setErrorPath(outputPath);
        runParams.setLogPath(outputPath);
        runParams.setSeccompRuleName(languageConfig.seccompRule());
        runParams.setArgs(runCommands);
        runParams.setEnv(languageConfig.env());

        runParams.setUid(SystemConfig.UID);
        runParams.setGid(SystemConfig.GID);

        Result run = Executor.exec(runParams);

        RunResult runResult = new RunResult();
        runResult.setStatus(0);
        if (run == null) {
            runResult.setStatus(3);
            runResult.setRunMsg("运行时未知错误, 请反馈给开发者");
            return runResult;
        }

        BeanUtils.copyProperties(run, runResult);

        if (run.getResult() != 0) {
            runResult.setStatus(2);
        }
        if (FileUtil.exist(outputPath)) {
            runResult.setOutput(FileUtil.readString(outputPath, StandardCharsets.UTF_8));
        }

        return runResult;
    }
}
