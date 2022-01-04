package leo.work.support.biz;

import android.view.View;
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
 * 代码备注:
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
    //点击发送短信的按钮
    private View view;

    public SmsCountdownBiz(LifeControlInterface lifeControlInterface, View view, int totalDuration, OnSmsCountdownBizCallBack callBack) {
        super(lifeControlInterface);
        this.view = view;
        this.totalDuration = totalDuration;
        this.callBack = callBack;
        this.callBack.onSmsPrepare();
    }

    //请求服务器
    public void requestService() {
        view.setClickable(false);
        callBack.onSmsPrepare();
    }

    //请求服务器得到的结果
    public void serviceResult(boolean success) {
        if (!success) {
            view.setClickable(true);
            callBack.onSmsPrepare();
            return;
        }
        startCountdown();
    }

    //开始倒计时
    private void startCountdown() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.cancel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int seconds = totalDuration - currentDuration;
                if (seconds <= 0) {
                    countdownEnd();
                } else {
                    callBack.onSmsTimeChange(seconds);
                    currentDuration++;
                }
            }
        }, 1000);
    }

    //倒计时结束
    private void countdownEnd() {
        if (callBack != null) {
            callBack.onSmsPrepare();
        }
        if (view != null) {
            view.setClickable(true);
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    public interface OnSmsCountdownBizCallBack {

        //准备
        void onSmsPrepare();

        //开始倒计时
        void onSmsTimeChange(int time);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }
}
