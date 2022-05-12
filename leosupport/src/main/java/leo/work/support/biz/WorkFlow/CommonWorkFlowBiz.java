package leo.work.support.biz.WorkFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 工作流
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/5/12
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-8
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonWorkFlowBiz {

    private WorkFlowControl control;

    public CommonWorkFlowBiz() {
        this(null);
    }

    public CommonWorkFlowBiz(OnBeforeToDoListener beforeToDoListener) {
        control = new WorkFlowControl();
        control.setBeforeToDoListener(beforeToDoListener);
    }

    // 添加任务
    public CommonWorkFlowBiz addWorkFlowTask(WorkFlowTask task) {
        control.addWorkFlowTask(task);
        return this;
    }

    // 开始执行任务
    public void startTask() {
        startTask(null);
    }

    // 开始执行任务
    public void startTask(OnEndTodoListener endTodoListener) {
        // 设置结束
        control.setEndTodoListener(endTodoListener);
        // 开始执行
        control.beforeToDo();
    }

    public interface OnBeforeToDoListener {

        void beforeToDo(WorkFlowControl control);

    }

    public interface OnEndTodoListener {

        void endToDo();

    }
}
