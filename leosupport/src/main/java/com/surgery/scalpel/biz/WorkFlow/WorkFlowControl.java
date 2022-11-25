package com.surgery.scalpel.biz.WorkFlow;

import java.util.ArrayList;
import java.util.List;

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
public class WorkFlowControl {

    //
    private CommonWorkFlowBiz.OnBeforeToDoListener beforeToDoListener;
    // 任务列表
    protected List<WorkFlowTask> workFlowTaskList;
    private int workFlowTaskIndex;
    // 结束
    private CommonWorkFlowBiz.OnEndTodoListener endTodoListener;


    // 执行下一个任务
    public void doNextTask() {
        WorkFlowTask lastTask = getLastWorkFlowTask();
        WorkFlowTask task = getWorkFlowTask();
        doTask(lastTask, task);
    }

    // 执行指定Id任务
    public void doTaskById(long id) {
        WorkFlowTask lastTask = getLastWorkFlowTask();
        WorkFlowTask task = getWorkFlowTaskById(id);
        doTask(lastTask, task);
    }

    // 设置开始监听器
    protected void setBeforeToDoListener(CommonWorkFlowBiz.OnBeforeToDoListener beforeToDoListener) {
        this.beforeToDoListener = beforeToDoListener;
    }

    // 设置结束监听器
    protected void setEndTodoListener(CommonWorkFlowBiz.OnEndTodoListener endTodoListener) {
        this.endTodoListener = endTodoListener;
    }

    // 添加任务
    protected void addWorkFlowTask(WorkFlowTask task) {
        if (workFlowTaskList == null) {
            workFlowTaskList = new ArrayList<>();
        }
        workFlowTaskList.add(task);
    }

    // 开始的时候需要做的事情
    protected void beforeToDo() {
        workFlowTaskIndex = -1;
        // 开始
        if (beforeToDoListener != null) {
            beforeToDoListener.beforeToDo(this);
            return;
        }
        doNextTask();
    }

    // 结束的时候需要做的事情
    private void endToDo() {
        if (endTodoListener == null) {
            return;
        }
        endTodoListener.endToDo();
    }

    //
    private WorkFlowTask getLastWorkFlowTask() {
//        WorkFlowTask lastTask = workFlowTaskList.get(workFlowTaskIndex);
        return null;
    }

    // 获取下一个任务
    private WorkFlowTask getWorkFlowTask() {
        int index = workFlowTaskIndex + 1;
        if (workFlowTaskList == null || index >= workFlowTaskList.size()) {
            return null;
        }
        workFlowTaskIndex = index;
        return workFlowTaskList.get(workFlowTaskIndex);
    }

    // 获取指定Id的任务
    private WorkFlowTask getWorkFlowTaskById(long id) {
        if (workFlowTaskList == null) {
            return null;
        }
        for (int i = 0; i < workFlowTaskList.size(); i++) {
            if (workFlowTaskList.get(i).getTaskId() == id) {
                workFlowTaskIndex = i;
                return workFlowTaskList.get(workFlowTaskIndex);
            }
        }
        return null;
    }

    // 执行任务
    private void doTask(WorkFlowTask lastTask, WorkFlowTask task) {
        if (task == null) {
            endToDo();
            return;
        }
        task.doTask(lastTask, this);
    }

}
