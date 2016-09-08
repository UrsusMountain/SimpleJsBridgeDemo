package com.ursus.simplejsbridgedemo.bean;

/**
 * Author:  ursus
 * Date:    16/8/18
 * Function:
 */
public class ResponseData{

    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILURE = "failure";

    private String status;
    private String data;
    private String err;
    private String successCallbackId;
    private String failureCallbackId;

    public ResponseData(){

    }

    public ResponseData(String status,String data,String err,String successCallbackId,String failureCallbackId){
        this.status = status;
        this.data = data;
        this.err = err;
        this.successCallbackId = successCallbackId;
        this.failureCallbackId = failureCallbackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
