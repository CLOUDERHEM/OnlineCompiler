package com.lc.compiler.config.language;

/**
 * @author Aaron Yeung
 * @date 1/21/2022 11:27 AM
 */
public interface LanguageConfig {

    /**
     * src name
     *
     * @return name
     */
    String srcName();

    /**
     * exe name
     *
     * @return exe name;
     */
    String exeName();

    /**
     * max cpu time
     *
     * @return int
     */
    Integer maxCpuTime();

    /**
     * mag real time
     *
     * @return int
     */
    Integer maxRealTime();

    /**
     * max memory
     *
     * @return int
     */
    Integer maxMemory();

    /**
     * compile command
     *
     * @return string
     */
    String compileCommand();

    /**
     * run command
     *
     * @return string
     */
    String command();

    /**
     * rule name
     *
     * @return string
     */
    String seccompRule();

    /**
     * system env
     *
     * @return string
     */
    String env();

    /**
     * for java
     *
     * @return int
     */

    Integer memoryLimitCheckOnly();

    int MAX_PROCESS_NUMBER = 10;
    int MAX_OUTPUT_SIZE = 20 * 1024 * 1024;
    int MAX_STACK = 128 * 1024 * 1024;

}
