package com.sametbakmaz.SanalPosFups.exceptionHandler;


public enum ErrorCodeEnum {
    NOT_FOUND(404),
    INVALID(400),
    FAIL(500);

    private final int code;

    ErrorCodeEnum(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
