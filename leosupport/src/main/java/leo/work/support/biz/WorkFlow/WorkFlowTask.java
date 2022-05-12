package leo.work.support.biz.WorkFlow;

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

    private long taskId;

    public WorkFlowTask() {
        this(System.currentTimeMillis());
    }

    public WorkFlowTask(long taskId) {
        this.taskId = taskId;
    }

    public abstract void doTask(WorkFlowControl control);

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
