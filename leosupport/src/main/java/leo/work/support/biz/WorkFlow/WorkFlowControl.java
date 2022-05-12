package leo.work.support.biz.WorkFlow;

import java.util.ArrayList;
import java.util.Iterator;
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
        WorkFlowTask task = getWorkFlowTask();
        doTask(task);
    }

    // 执行指定Id任务
    public void doTaskById(long id) {
        WorkFlowTask task = getWorkFlowTaskById(id);
        doTask(task);
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
        workFlowTaskIndex = 0;
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


    // 获取下一个任务
    private WorkFlowTask getWorkFlowTask() {
        if (workFlowTaskList == null || workFlowTaskIndex >= workFlowTaskList.size()) {
            return null;
        }
        WorkFlowTask task = workFlowTaskList.get(workFlowTaskIndex);
        workFlowTaskIndex++;
        return task;
    }

    // 获取指定Id的任务
    private WorkFlowTask getWorkFlowTaskById(long id) {
        if (workFlowTaskList == null) {
            return null;
        }
        for (int i = 0; i < workFlowTaskList.size(); i++) {
            if (workFlowTaskList.get(i).getTaskId() == id) {
                workFlowTaskIndex = i + 1;
                return workFlowTaskList.get(i);
            }
        }
        return null;
    }

    // 执行任务
    private void doTask(WorkFlowTask task) {
        if (task == null) {
            endToDo();
            return;
        }
        task.doTask(this);
    }

}
