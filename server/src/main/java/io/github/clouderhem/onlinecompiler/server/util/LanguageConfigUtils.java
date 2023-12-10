package io.github.clouderhem.onlinecompiler.server.util;

import io.github.clouderhem.onlinecompiler.server.config.language.LanguageConfig;
import io.github.clouderhem.onlinecompiler.server.config.language.configs.*;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 2:57 PM
 */
public class LanguageConfigUtils {

    private static final C_Lang_Config C_LANG_CONFIG = new C_Lang_Config();
    private static final Cpp_Lang_Config CPP_LANG_CONFIG = new Cpp_Lang_Config();
    private static final Java_Lang_Config JAVA_LANG_CONFIG = new Java_Lang_Config();
    private static final Python_Lang_Config PYTHON_LANG_CONFIG = new Python_Lang_Config();
    private static final JavaScript_Lang_Config JAVA_SCRIPT_LANG_CONFIG = new JavaScript_Lang_Config();
    private static final Go_Lang_Config GO_LANG_CONFIG = new Go_Lang_Config();

    public static LanguageConfig getConfigInstance(int languageNum) {

        LanguageConfig languageConfig = null;

        if (languageNum == C_Lang_Config.NUMBER) {
            languageConfig = C_LANG_CONFIG;
        } else if (languageNum == Cpp_Lang_Config.NUMBER) {
            languageConfig = CPP_LANG_CONFIG;
        } else if (languageNum == Java_Lang_Config.NUMBER) {
            languageConfig = JAVA_LANG_CONFIG;
        } else if (languageNum == Python_Lang_Config.NUMBER) {
            languageConfig = PYTHON_LANG_CONFIG;
        } else if (languageNum == JavaScript_Lang_Config.NUMBER) {
            languageConfig = JAVA_SCRIPT_LANG_CONFIG;
        } else if (languageNum == Go_Lang_Config.NUMBER) {
            languageConfig = GO_LANG_CONFIG;
        }

        return languageConfig;
    }
}
