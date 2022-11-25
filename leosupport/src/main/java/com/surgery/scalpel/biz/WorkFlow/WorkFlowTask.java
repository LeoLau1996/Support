package com.surgery.scalpel.biz.WorkFlow;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/5/12
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-8
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class WorkFlowTask {

    // 任务Id
    private long taskId;
    // 拓展字段，你可以用来保存数据
    private Object data;

    public WorkFlowTask() {
        this(System.currentTimeMillis());
    }

    public WorkFlowTask(long taskId) {
        this.taskId = taskId;
    }

    public abstract void doTask(WorkFlowTask lastWorkFlowTask, WorkFlowControl control);

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
