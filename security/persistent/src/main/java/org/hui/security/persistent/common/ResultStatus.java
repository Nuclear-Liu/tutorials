package org.hui.security.persistent.common;

import org.springframework.http.HttpStatus;

public enum ResultStatus {
    OK(HttpStatus.OK, 200, "成功"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500,"服务器内部错误");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ResultStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultStatus{" +
                "httpStatus=" + httpStatus +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
