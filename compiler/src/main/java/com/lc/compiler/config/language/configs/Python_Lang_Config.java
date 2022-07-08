package com.lc.compiler.config.language.configs;


import com.lc.compiler.config.language.LanguageConfig;

/**
 * @author Aaron Yeung
 * @date 1/21/2022 10:21 AM
 */
@SuppressWarnings("all")
public class Python_Lang_Config implements LanguageConfig {
    public static String NAME = "python";

    public static class Compile {

        public static String srcName = "solution.py";

        public static String exeName = "__pycache__/solution.cpython-38.pyc";

        public static Integer maxCpuTime = 3000;

        public static Integer maxRealTime = 5000;

        public static Integer maxMemory = 128 * 1024 * 1024;

        public static String compileCommand = "/usr/bin/python3 -m py_compile {srcPath}";
    }


    public static class Run {

        public static String command = "/usr/bin/python3 {exePath}";

        public static String seccompRule = "general";

        public static String[] env = {"PYTHONIOENCODING=UTF-8"};

        public static Integer memoryLimitCheckOnly = 1;
    }

    @Override
    public String srcName() {
        return Compile.srcName;
    }

    @Override
    public String exeName() {
        return Compile.exeName;
    }

    @Override
    public Integer maxCpuTime() {
        return Compile.maxCpuTime;
    }

    @Override
    public Integer maxRealTime() {
        return Compile.maxRealTime;
    }

    @Override
    public Integer maxMemory() {
        return Compile.maxMemory;
    }

    @Override
    public String compileCommand() {
        return Compile.compileCommand;
    }

    @Override
    public String command() {
        return Run.command;
    }

    @Override
    public String seccompRule() {
        return Run.seccompRule;
    }

    @Override
    public Integer memoryLimitCheckOnly() {
        return -1;
    }

    @Override
    public String[] env() {
        return Run.env;
    }
}
