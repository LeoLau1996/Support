package leo.work.support.Support.ToolSupport;

import android.app.Activity;

import java.util.Stack;

/**
 * Author : zhouyx
 * Date   : 2015/12/11
 * Activity管理类
 */
public class ActivitySupport {

    private static Stack<Activity> activityStack;
    private static ActivitySupport instance;

    private ActivitySupport() {
    }

    public static ActivitySupport get() {
        if (instance == null) {
            instance = new ActivitySupport();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 从堆栈中移除该Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
//                if(activity instanceof PresenterBaseActivity){
//                    ((PresenterBaseActivity)activity).finish();
//                } else {
                    activity.finish();
//                }
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有除目标外的Activity
     */
    public void finishActivitiesWithoutTarget(Class<?> cls) {
        Stack<Activity> tempActivityStack = new Stack<>();
        tempActivityStack.addAll(activityStack);
        for (Activity activity : tempActivityStack) {
            if (!activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
        tempActivityStack.clear();
    }

    /**
     * 结束所有Activity 除了cls和cls2这个主activity之外
     */
    public void finishAllActivityExceptParamActivity(Class<?> cls, Class<?> cls2) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity != null && !activity.getClass().equals(cls) && !activity.getClass().equals(cls2)) {
                activity.finish();
            }
        }
    }

    /**
     *  从栈顶开始关闭activity,直到目标activity
     */
    public void finishActivityFromTopToTarget(Class<?> cls){

//        for (int i = activityStack.size() - 1; i >=0; i--) {
//            Activity activity = activityStack.get(i);
//            LogUtil.i("activity.getClass()===>" + activity.getClass().getName());
//        }

        for (int i = activityStack.size() - 1; i >=0; i--) {
            Activity activity = activityStack.get(i);
            if(activity == null || activity.getClass().equals(cls)){
                return;
            }
            activity.finish();
        }
    }



    /**
     * 获取栈顶activity
     * @return
     */
    public Activity getTopActivity(){
        return activityStack.peek();
    }
}
