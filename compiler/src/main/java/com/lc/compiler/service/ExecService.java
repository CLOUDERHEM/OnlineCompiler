package com.lc.compiler.service;

import com.lc.Result;
import com.lc.compiler.config.language.LanguageConfig;
import com.lc.compiler.model.vo.RequestCodeVO;

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
