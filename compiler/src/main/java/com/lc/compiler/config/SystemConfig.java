package com.lc.compiler.config;

/**
 * @author Aaron Yeung
 * @date 1/21/2022 4:05 PM
 */
public final class SystemConfig {

    public static final String TMP_PATH = "/tmp/OnlineCompile/";
    public static final int UID = 0;
    public static final int GID = 0;
    public static final int MAX_OBJECT_EXIST_TIME = 10000;


    public static final int CORE_POOL_SIZE = 13;
    public static final int MAX_POOL_SIZE = 15;
    public static final int QUEUE_CAPACITY = 50;

    public static final String INPUT_FILE_NAME = "1.in";
    public static final String OUTPUT_FILE_NAME = "1.out";
    public static final String COMPILE_LOG_NAME = "compile.log";

}
