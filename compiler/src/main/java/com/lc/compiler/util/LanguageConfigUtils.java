package com.lc.compiler.util;

import com.lc.compiler.config.language.LanguageConfig;
import com.lc.compiler.config.language.configs.C_Lang_Config;
import com.lc.compiler.config.language.configs.Cpp_Lang_Config;
import com.lc.compiler.config.language.configs.Java_Lang_Config;
import com.lc.compiler.config.language.configs.Python_Lang_Config;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 2:57 PM
 */
public class LanguageConfigUtils {

    private static final C_Lang_Config C_LANG_CONFIG = new C_Lang_Config();
    private static final Cpp_Lang_Config CPP_LANG_CONFIG = new Cpp_Lang_Config();
    private static final Java_Lang_Config JAVA_LANG_CONFIG = new Java_Lang_Config();
    private static final Python_Lang_Config PYTHON_LANG_CONFIG = new Python_Lang_Config();

    public static LanguageConfig getConfigInstance(int languageNum) {

        LanguageConfig languageConfig = null;

        if (languageNum == 1) {
            languageConfig = C_LANG_CONFIG;
        } else if (languageNum == 2) {
            languageConfig = CPP_LANG_CONFIG;
        } else if (languageNum == 3) {
            languageConfig = JAVA_LANG_CONFIG;
        } else if (languageNum == 4) {
            languageConfig = PYTHON_LANG_CONFIG;
        }

        return languageConfig;
    }
}
