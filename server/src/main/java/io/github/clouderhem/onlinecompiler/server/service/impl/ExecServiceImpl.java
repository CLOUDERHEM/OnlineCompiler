package io.github.clouderhem.onlinecompiler.server.service.impl;

import cn.hutool.core.io.FileUtil;
import io.github.clouderhem.executor.Result;
import io.github.clouderhem.onlinecompiler.server.config.SystemConfig;
import io.github.clouderhem.onlinecompiler.server.config.language.LanguageConfig;
import io.github.clouderhem.onlinecompiler.server.model.CompileResult;
import io.github.clouderhem.onlinecompiler.server.model.vo.RequestCodeVO;
import io.github.clouderhem.onlinecompiler.server.service.Compiler;
import io.github.clouderhem.onlinecompiler.server.service.ExecService;
import io.github.clouderhem.onlinecompiler.server.service.Runner;
import io.github.clouderhem.onlinecompiler.server.util.LanguageConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 2:00 PM
 */
@Slf4j
@Service
public class ExecServiceImpl implements ExecService {
    @Override
    public Result exec(RequestCodeVO code, String dirName) {

        if (code == null || code.getLanguage() == null || !StringUtils.hasText(code.getCode())) {
            return null;
        }
        LanguageConfig configInstance = LanguageConfigUtils.getConfigInstance(code.getLanguage());
        if (configInstance == null) {
            return null;
        }

        String relativeDirPath = dirName + "/";
        String inputPath = null;
        String absoluteOutputDir = SystemConfig.TMP_PATH + relativeDirPath;
        String outputPath = absoluteOutputDir + SystemConfig.OUTPUT_FILE_NAME;
        String srcPath = absoluteOutputDir + configInstance.srcName();

        FileUtil.writeString(code.getCode(), srcPath, StandardCharsets.UTF_8);
        // set input file
        if (StringUtils.hasText(code.getInput())) {
            inputPath = SystemConfig.TMP_PATH + relativeDirPath + SystemConfig.INPUT_FILE_NAME;
            FileUtil.writeString(code.getInput(), inputPath, StandardCharsets.UTF_8);
        }

        Result serve = exec(configInstance, srcPath, absoluteOutputDir, inputPath, outputPath);

        if (FileUtil.exist(absoluteOutputDir)) {
            FileUtil.del(absoluteOutputDir);
        }

        return serve;

    }

    @Override
    public Result exec(LanguageConfig languageConfig, String srcPath, String outputDir, String inputPath, String outputPath) {

        // compile
        CompileResult compile = Compiler.compile(languageConfig, srcPath, outputDir);
        if (compile.getResult() != 0) {
            return compile;
        }

        // run
        return Runner.run(languageConfig, compile.getExePath(), inputPath, outputPath, outputDir);

    }
}
