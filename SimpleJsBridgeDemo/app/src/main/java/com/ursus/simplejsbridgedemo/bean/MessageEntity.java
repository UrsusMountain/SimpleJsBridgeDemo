package com.ursus.simplejsbridgedemo.bean;

/**
 * Author:  ursus
 * Date:    16/8/29
 * Function:
 */
public class MessageEntity<T> {

    public T data;
    private String successCallbackId;
    private String failureCallbackId;

    public MessageEntity(Message message,T data){
        this.data = data;
        this.successCallbackId = message.getSuccessCallbackId();
        this.failureCallbackId = message.getFailureCallbackId();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSuccessCallbackId() {
        return successCallbackId;
    }

    public void setSuccessCallbackId(String successCallbackId) {
        this.successCallbackId = successCallbackId;
    }

    public String getFailureCallbackId() {
        return failureCallbackId;
    }

    public void setFailureCallbackId(String failureCallbackId) {
        this.failureCallbackId = failureCallbackId;
    }
}
