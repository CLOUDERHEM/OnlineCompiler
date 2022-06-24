package com.lc.compiler.config.language.configs;


import com.lc.compiler.config.language.LanguageConfig;

/**
 * @author Aaron Yeung
 * @date 1/21/2022 10:21 AM
 */
@SuppressWarnings("all")
public class Java_Lang_Config implements LanguageConfig {
    public static String NAME="java";

    public static class Compile {

        public static String srcName = "Main.java";

        public static String exeName = "Main";

        public static Integer maxCpuTime = 3000;

        public static Integer maxRealTime = 5000;

        public static Integer maxMemory = -1;

        public static String compileCommand = "/usr/bin/javac {srcPath} -d {exeDir} -encoding UTF8";
    }


    public static class Run {

        public static String command = "/usr/bin/java -cp {exeDir} -Djava.security.manager -Dfile.encoding=UTF-8 -Djava.security.policy==/etc/java_policy -Djava.awt.headless=true Main";

        public static String seccompRule;

        public static String env;

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
    public String env() {
        return Run.env;
    }

    @Override
    public Integer memoryLimitCheckOnly() {
        return 1;
    }
}
