package com.lc.compiler.config;

/**
 * @author Aaron Yeung
 * @date 1/21/2022 4:05 PM
 */
public final class SystemConfig {

    /**
     * 生成文件目录
     */
    public static final String TMP_PATH = "/tmp/OnlineCompile/";
    
    /**
     * 默认root用户
     */
    public static final int UID = 0;

    /**
     * 默认root用户组
     */
    public static final int GID = 0;

    /**
     * 运行结果保存的最大时长, 默认15s
     */
    public static final int MAX_OBJECT_EXIST_TIME = 15 * 1000;

    /**
     * 核心线程数量
     */
    public static final int CORE_POOL_SIZE = 5;

    /**
     * 应靠近cpu核心数
     */
    public static final int MAX_POOL_SIZE = 13;

    /**
     * 提交的任务数目超过 QUEUE_CAPACITY + MAX_POOL_SIZE 时候, 任务将被丢弃
     */
    public static final int QUEUE_CAPACITY = 100;

    /**
     * 输入文件的名称
     */
    public static final String INPUT_FILE_NAME = "1.in";

    /**
     * 输出到的文件名称
     */
    public static final String OUTPUT_FILE_NAME = "1.out";

    /**
     * 编译发生错误时输出文件名称
     */
    public static final String COMPILE_LOG_NAME = "compile.log";

}
