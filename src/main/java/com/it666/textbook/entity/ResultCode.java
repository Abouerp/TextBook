package com.it666.textbook.entity;

/**
 * 接口返回码
 * 0  : 成功
 * >0 : 表示已知的异常
 * <0 : 表示未知的异常
 *
 * @author Abouerp
 */
public enum ResultCode {

    /**
     * not found
     */
    NOT_FOUND(-2, "Not Found"),
    /**
     * Unauthorized
     */
    UNAUTHORIZED(-1, "Unauthorized"),

    /**
     * Success
     */
    SUCCESS(0, "Success"),

    /**
     * 检查失败
     */
    CHECK_FAIL(1, "Illegal Argument"),

    /**
     * Forbidden
     */
    NO_PERMISSION(2, "Forbidden"),

    /**
     * 帐号禁用
     */
    ACCOUNT_DISABLE(3, "Account Disable"),

    /**
     * 多处登录
     */
    MAXIMUM_SESSION(4, " Maximum sessions"),

    /**
     * session 失效
     */
    SESSION_EXPIRED(5, " Session expired"),

    /**
     * is register
     */
    IS_REGISTER(6, "Is register"),

    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION(-99, "Unknown Exception");

    private final Integer code;
    private final String tips;

    ResultCode(int code, String tips) {
        this.code = code;
        this.tips = tips;
    }

    public String getTips() {
        return tips;
    }

    public int getCode() {
        return code;
    }
}
