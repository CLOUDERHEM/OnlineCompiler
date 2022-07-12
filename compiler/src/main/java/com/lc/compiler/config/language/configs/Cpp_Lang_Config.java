package com.lc.compiler.config.language.configs;


import com.lc.compiler.config.language.LanguageConfig;

/**
 * @author Aaron Yeung
 * @date 1/21/2022 10:21 AM
 */
@SuppressWarnings("all")
public class Cpp_Lang_Config implements LanguageConfig {
    public static String NAME = "cpp";
    public static final int NUMBER = 2;

    public static class Compile {

        public static String srcName = "main.cpp";

        public static String exeName = "main";

        public static Integer maxCpuTime = 3000;

        public static Integer maxRealTime = 5000;

        public static Integer maxMemory = 128 * 1024 * 1024;

        public static String compileCommand = "/usr/bin/g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++11 {srcPath} -lm -o {exePath}";
    }


    public static class Run {

        public static String command = "{exePath}";

        public static String seccompRule = "c_cpp";

        public static String[] env;
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
