package org.hui.tdd.junit4.demo;

/**
 * error response
 * @author Hui.Liu
 * @since 2021-11-28 1:04
 */
public class ErrorResponse implements Response {
    private Request originalRequest;
    private Exception originalException;
    public ErrorResponse(Request request, Exception exception) {
        this.originalRequest = request;
        this.originalException = exception;
    }

    public Exception getOriginalException() {
        return originalException;
    }
}
