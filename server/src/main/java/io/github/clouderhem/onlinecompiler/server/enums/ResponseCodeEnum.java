package io.github.clouderhem.onlinecompiler.server.enums;

/**
 * @author Aaron Yeung
 * @date 6/24/2022 1:13 PM
 */
public enum ResponseCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200),

    /**
     * 错误
     */
    ERROR(500),

    /**
     * 禁止
     */
    FORBIDDEN(403),

    /**
     * not found
     */
    NOT_FOUND(404);

    private final int code;

    public int getCode() {
        return code;
    }

    ResponseCodeEnum(int i) {
        this.code = i;
    }
}
