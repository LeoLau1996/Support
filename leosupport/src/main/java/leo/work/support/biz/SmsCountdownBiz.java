package leo.work.support.biz;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
public class SmsCountdownBiz extends CommonLifeBiz implements Handler.Callback {

    //总时长
    private int totalDuration;
    //当前时长
    private int currentDuration;
    //倒计时
    private Timer timer;
    //倒计时回调
    private final OnSmsCountdownBizCallBack callBack;
    //点击发送短信的按钮
    private final View view;
    //
    private Handler handler;

    public SmsCountdownBiz(LifeControlInterface lifeControlInterface, View view, int totalDuration, OnSmsCountdownBizCallBack callBack) {
        super(lifeControlInterface);
        this.view = view;
        this.totalDuration = totalDuration;
        this.callBack = callBack;
        sendMessageToHandler(2, null);
    }

    //设置倒计时长
    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    //请求服务器
    public void requestService() {
        sendMessageToHandler(3, false);
        sendMessageToHandler(2, null);
    }

    //请求服务器得到的结果
    public void serviceResult(boolean success) {
        currentDuration = 0;
        if (!success) {
            sendMessageToHandler(3, true);
            sendMessageToHandler(2, null);
            return;
        }
        startCountdown();
    }

    //开始倒计时
    private void startCountdown() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int seconds = totalDuration - currentDuration;
                //倒计时结束
                if (seconds <= 0) {
                    countdownEnd();
                    return;
                }
                //
                sendMessageToHandler(1, seconds);
                //
                currentDuration++;
            }
        }, 0, 1000);
    }

    //倒计时结束
    private void countdownEnd() {
        sendMessageToHandler(2, null);
        sendMessageToHandler(3, true);
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        currentDuration = 0;
    }

    //
    private void sendMessageToHandler(int what, Object obj) {
        if (handler == null) {
            handler = new Handler(this);
        }
        Message message = obj != null ? handler.obtainMessage(what, obj) : handler.obtainMessage(what);
        handler.sendMessage(message);
    }

    //
    @Override
    public boolean handleMessage(@NonNull Message message) {
        switch (message.what) {
            case 1: {
                if (callBack != null) {
                    callBack.onSmsTimeChange((Integer) message.obj);
                }
                break;
            }
            case 2: {
                if (callBack != null) {
                    callBack.onSmsPrepare();
                }
                break;
            }
            case 3: {
                if (view != null) {
                    view.setClickable((Boolean) message.obj);
                }
                break;
            }
        }
        return false;
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

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = null;
    }
}