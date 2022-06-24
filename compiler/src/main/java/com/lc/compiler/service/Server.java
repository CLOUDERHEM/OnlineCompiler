package com.lc.compiler.service;

import cn.hutool.core.io.FileUtil;
import com.lc.Result;
import com.lc.compiler.config.SystemConfig;
import com.lc.compiler.config.language.LanguageConfig;
import com.lc.compiler.model.CompileResult;
import com.lc.compiler.model.RequestCodeVO;
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
public class Server {
    public Result serve(RequestCodeVO code) {

        if (code == null || code.getLanguage() == null || !StringUtils.hasText(code.getCode())) {
            return null;
        }

        String dirName = System.currentTimeMillis() + "/";
        String inputPath = SystemConfig.TMP_PATH + dirName + "1.in";
        String outputDir = SystemConfig.TMP_PATH + dirName;

        LanguageConfig configInstance = LanguageConfigUtils.getConfigInstance(code.getLanguage());
        if (configInstance == null) {
            return null;
        }

        String srcPath = outputDir + configInstance.srcName();
        FileUtil.writeString(code.getCode(), srcPath, StandardCharsets.UTF_8);
        // set input file
        FileUtil.writeString(code.getInput(), inputPath, StandardCharsets.UTF_8 );

        Result serve = serve(configInstance, srcPath, outputDir, inputPath);

        log.debug("{}", serve);

        return serve;

    }


    public Result serve(LanguageConfig languageConfig, String srcPath, String outputDir, String inputPath) {

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
