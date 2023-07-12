

package com.authentication.jwt.models;

public class ResponseWrapper<T> {
    private T body;
    private String status;
    private String message;

    public ResponseWrapper() {
    }

    public ResponseWrapper(T t, String status, String message) {
        this.body = t;
        this.status = status;
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
