package org.hui.design.patterns.chainofresposibility.pattern;

public class Request {
    private String description;
    private RequestType requestType;
    public Request(String description, RequestType requestType) {
        this.description = description;
        this.requestType = requestType;
    }
    public RequestType getRequestType() {
        return this.requestType;
    }
    public String getDescription() {
        return this.description;
    }
}
