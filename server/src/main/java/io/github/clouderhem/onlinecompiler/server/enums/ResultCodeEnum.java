package io.github.clouderhem.onlinecompiler.server.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 8:31 PM
 */
public class ResultCodeEnum {

    private static final Map<Integer, String> RESULT_INFO_MAP = new HashMap<>();
    private static final Map<Integer, String> STATUS_INFO_MAP = new HashMap<>();

    static {
        RESULT_INFO_MAP.put(0, "运行成功 ");
        RESULT_INFO_MAP.put(1, "时间过长 ");
        RESULT_INFO_MAP.put(2, "总时间过长 ");
        RESULT_INFO_MAP.put(3, "内存过大 ");
        RESULT_INFO_MAP.put(4, "运行时错误 ,可能使用被禁止的指令");
        RESULT_INFO_MAP.put(5, "系统错误, 请联系开发者 ");

        STATUS_INFO_MAP.put(0, "运行成功 ");
        STATUS_INFO_MAP.put(1, "编译过程错误 ");
        STATUS_INFO_MAP.put(2, "运行过程错误 ");
        STATUS_INFO_MAP.put(3, "系统错误, 请联系开发者 ");
    }

    public static String getResultMsg(Integer code) {
        return RESULT_INFO_MAP.get(code);
    }

    public static String getStatusMsg(Integer code) {
        return STATUS_INFO_MAP.get(code);
    }

}
