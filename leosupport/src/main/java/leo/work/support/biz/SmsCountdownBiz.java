package leo.work.support.biz;

import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 短信倒计时业务
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/18
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注: TODO 设计成单例
 * ---------------------------------------------------------------------------------------------
 **/
public class SmsCountdownBiz extends CommonLifeBiz {

    //总时长
    private int totalDuration;
    //当前时长
    private int currentDuration;
    //倒计时
    private Timer timer;
    //倒计时回调
    private OnSmsCountdownBizCallBack callBack;

    public SmsCountdownBiz(LifeControlInterface lifeControlInterface, int totalDuration, OnSmsCountdownBizCallBack callBack) {
        super(lifeControlInterface);
        this.totalDuration = totalDuration;
        this.callBack = callBack;
    }

    //请求服务器
    public void requestService() {

    }

    //
    public void serviceResult(boolean success) {
        if (!success) {
            return;
        }
        xxx();
    }

    //开始倒计时
    private void xxx() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.cancel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int seconds = totalDuration - currentDuration;
                if (seconds <= 0) {
                    callBack.end();
                    timer.cancel();
                } else {
                    callBack.onTimeChange(seconds);
                    currentDuration++;
                }
            }
        }, 1000);
    }

    public interface OnSmsCountdownBizCallBack {

        //准备
        void onSmsPrepare();

        //开始倒计时
        void onTimeChange(int time);

        //
        void end();
    }
}
