package io.github.clouderhem.onlinecompiler.server.config.language.configs;

import io.github.clouderhem.onlinecompiler.server.config.language.LanguageConfig;

/**
 * @author Aaron Yeung
 * @date 6/26/2022 2:13 PM
 */
@SuppressWarnings("all")
public class JavaScript_Lang_Config implements LanguageConfig {
    public static String NAME = "js";
    public static final int NUMBER = 5;

    public static class Compile {

        public static String srcName = "main.js";

        public static String exeName = "main.js";

        public static Integer maxCpuTime = 3000;

        public static Integer maxRealTime = 5000;

        public static Integer maxMemory = 128 * 1024 * 1024;

        public static String compileCommand = "/usr/bin/node --check {srcPath}";
    }


    public static class Run {

        public static String command = "/usr/bin/node {exePath}";

        public static String seccompRule = "node";

        public static String[] env = {"LANG=en_US.UTF-8", "LANGUAGE=en_US:en", "LC_ALL=en_US.UTF-8"};

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
        return Run.memoryLimitCheckOnly;
    }

    @Override
    public String[] env() {
        return Run.env;
    }
}
