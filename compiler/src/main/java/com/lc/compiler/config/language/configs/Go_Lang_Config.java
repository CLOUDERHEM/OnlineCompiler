package com.lc.compiler.config.language.configs;


import com.lc.compiler.config.language.LanguageConfig;

/**
 * @author Aaron Yeung
 * @date 1/21/2022 10:21 AM
 */
@SuppressWarnings("all")
public class Go_Lang_Config implements LanguageConfig {
    public static String NAME = "golang";
    public static final int NUMBER = 6;

    public static class Compile {

        public static String srcName = "main.go";

        public static String exeName = "main";

        public static Integer maxCpuTime = 3000;

        /**
         * 太小会抛出异常
         * runtime/cgo: pthread_create failed: Resource temporarily unavailable
         */
        public static Integer maxRealTime = 5000;

        public static Integer maxMemory = 1024 * 1024 * 1024;

        public static String compileCommand = "/usr/bin/go build -o {exePath} {srcPath}";
    }


    public static class Run {

        public static String command = "{exePath}";

        public static String seccompRule = "golang";

        public static String[] env = {"GOCACHE=/tmp", "GOPATH=/tmp", "GOMAXPROCS=1"};

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
        return 1;
    }

    @Override
    public String[] env() {
        return Run.env;
    }
}
