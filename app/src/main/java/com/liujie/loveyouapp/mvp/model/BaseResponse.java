package com.liujie.loveyouapp.mvp.model;

/**
 * 返回的数据格式基类
 *
 * @param <T>
 */
public class BaseResponse<T> {
    private T Data; //数据源
    private int Status;//状态码 正常1 错误2
    private String ErrorMessage;//说明

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        this.Data = data;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
