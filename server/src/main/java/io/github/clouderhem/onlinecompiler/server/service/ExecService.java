package io.github.clouderhem.onlinecompiler.server.service;

import io.github.clouderhem.executor.Result;
import io.github.clouderhem.onlinecompiler.server.config.language.LanguageConfig;
import io.github.clouderhem.onlinecompiler.server.model.vo.RequestCodeVO;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 2:00 PM
 */
public interface ExecService {
    /**
     * before exec
     *
     * @param code    code
     * @param dirName dir
     * @return result
     */
    Result exec(RequestCodeVO code, String dirName);


    /**
     * exec
     *
     * @param languageConfig config
     * @param srcPath        path
     * @param outputDir      dir
     * @param inputPath      path
     * @param outputPath     path
     * @return result
     */
    Result exec(LanguageConfig languageConfig, String srcPath, String outputDir, String inputPath, String outputPath);
}
