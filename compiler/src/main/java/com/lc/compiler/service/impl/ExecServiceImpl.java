package com.lc.compiler.service.impl;

import cn.hutool.core.io.FileUtil;
import com.lc.Result;
import com.lc.compiler.config.SystemConfig;
import com.lc.compiler.config.language.LanguageConfig;
import com.lc.compiler.model.CompileResult;
import com.lc.compiler.model.vo.RequestCodeVO;
import com.lc.compiler.service.Compiler;
import com.lc.compiler.service.ExecService;
import com.lc.compiler.service.Runner;
import com.lc.compiler.util.LanguageConfigUtils;
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

        String relativeDirPath = dirName + "/";
        String inputPath = SystemConfig.TMP_PATH + relativeDirPath + "1.in";
        String absoluteOutputDir = SystemConfig.TMP_PATH + relativeDirPath;

        LanguageConfig configInstance = LanguageConfigUtils.getConfigInstance(code.getLanguage());
        if (configInstance == null) {
            return null;
        }

        String srcPath = absoluteOutputDir + configInstance.srcName();
        FileUtil.writeString(code.getCode(), srcPath, StandardCharsets.UTF_8);
        // set input file
        FileUtil.writeString(code.getInput(), inputPath, StandardCharsets.UTF_8);

        Result serve = exec(configInstance, srcPath, absoluteOutputDir, inputPath);

        log.debug("{}", serve);

        // todo del dir

        return serve;

    }

    @Override
    public Result exec(LanguageConfig languageConfig, String srcPath, String outputDir, String inputPath) {

        // compile
        CompileResult compile = Compiler.compile(languageConfig, srcPath, outputDir);

        if (compile.getResult() != 0) {
            return compile;
        }
        String outputPath = outputDir + "1.out";

        // run
        return Runner.run(languageConfig, compile.getExePath(), inputPath, outputPath, outputDir);

    }
}
