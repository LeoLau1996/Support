package com.surgery.scalpel.model;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/4
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MethodResult {

    private boolean success;
    private Object returnResult;

    public MethodResult(boolean success, Object returnResult) {
        this.success = success;
        this.returnResult = returnResult;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(Object returnResult) {
        this.returnResult = returnResult;
    }
}
