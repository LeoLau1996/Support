package leo.work.support.Support.Thread;

import android.os.Handler;

import java.util.Timer;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:多线程支持
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/4/28.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class ThreadSupport {
    private Handler mHandler = null;
    private Timer timer = null;

    /**
     * 定时器   推荐使用
     * https://www.cnblogs.com/benhero/p/4521727.html
     */
    public void handel(Runnable runnable, long delayMillis) {
        if (mHandler == null)
            mHandler = new Handler();
        mHandler.postDelayed(runnable, delayMillis);
    }

    /**
     * 定时器   Timer+Handel
     */
    public void timer_Start(int delay, int period, LeoTimerTask task) {
        timer = new Timer();
        timer.schedule(task, delay, period);
    }

    public void timer_Cancel() {
        timer.cancel();
        timer = null;
    }
}
