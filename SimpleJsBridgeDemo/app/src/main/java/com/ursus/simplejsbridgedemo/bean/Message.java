package com.ursus.simplejsbridgedemo.bean;

/**
 * Author:  ursus
 * Date:    16/8/18
 * Function:
 */
public class Message {

    private String data;
    private String function;
    private int functionId;
    private String successCallbackId;
    private String failureCallbackId;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
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

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }
}
