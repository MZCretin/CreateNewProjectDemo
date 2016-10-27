package com.cretin.www.createnewprojectdemo.model;

/**
 * Created by cretin on 16/10/27.
 */

public class ResultModel<T> {
    private String message;
    private int status;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
