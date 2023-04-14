package org.hui.security.persistent.common;

public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    private Result(ResultStatus resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>(ResultStatus.OK, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultStatus.OK, data);
    }

    public static <T> Result<T> ok(ResultStatus resultStatus, T data) {
        return new Result<>(resultStatus, data);
    }

    public static <T> Result<T> failure() {
        return new Result<>(ResultStatus.INTERNAL_SERVER_ERROR, null);
    }

    public static <T> Result<T> failure(T data) {
        return new Result<>(ResultStatus.INTERNAL_SERVER_ERROR, data);
    }

    public static <T> Result<T> failure(ResultStatus resultStatus, T data) {
        return new Result<>(resultStatus, data);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
