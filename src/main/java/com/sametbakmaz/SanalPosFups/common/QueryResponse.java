package com.sametbakmaz.SanalPosFups.common;

public class QueryResponse<T> {
    private String status;
    private String message;
    private T data;
    private Integer exceptionCode;

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setExceptionCode(Integer exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    // Getters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Integer getExceptionCode() {
        return exceptionCode;
    }
public static <T> QueryResponse<T> createResponse(boolean isSuccess, T data, String message) {
        QueryResponse<T> queryResponse = new QueryResponse<>();
        queryResponse.setStatus(isSuccess ? "success" : "fail");
        queryResponse.setData(data);
        queryResponse.setMessage(message);
        queryResponse.setExceptionCode(null);
        return queryResponse;
    }

    public static <T> QueryResponse<T> createResponse(boolean isSuccess, T data, String message, Integer exceptionCode) {
        QueryResponse<T> queryResponse = new QueryResponse<>();
        queryResponse.setStatus(isSuccess ? "success" : "fail");
        queryResponse.setData(data);
        queryResponse.setMessage(message);
        queryResponse.setExceptionCode(exceptionCode);
        return queryResponse;
    }
}