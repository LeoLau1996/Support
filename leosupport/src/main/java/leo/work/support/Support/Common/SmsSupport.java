package leo.work.support.Support.Common;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import leo.work.support.Support.Thread.LeoTimerTask;
import leo.work.support.Support.Thread.ThreadSupport;

/**
 * 短信发送支持
 * Created by 刘桂安 on 2018/6/21.
 */

public class SmsSupport {
    private int wallTime;//等待时间
    private int nowTime = 0;//现在的时间
    private TextView tv;
    private Button btn;
    private OnSmsSupportCallback callback;
    private final String TYPE_CanSendSMS = "可发送", TYPE_NotSendSMS = "不可发送";
    private String type;
    private ThreadSupport threadSupport;

    public SmsSupport(final int wallTime, final View view, OnSmsSupportCallback mCallback) {
        this.wallTime = wallTime;
        if (view instanceof TextView) {
            tv = (TextView) view;
            btn = null;
        } else if (view instanceof Button) {
            tv = null;
            btn = (Button) view;
        }
        this.callback = mCallback;
        type = TYPE_CanSendSMS;
        threadSupport = new ThreadSupport();
        threadSupport.timer_Start(0, 1000, new LeoTimerTask(new LeoTimerTask.LeoTimerTaskListener() {
            @Override
            protected void doIt() {
                if (type.equals(TYPE_NotSendSMS)) {
                    nowTime++;
                    if (nowTime >= wallTime) {
                        nowTime = 0;
                        type = TYPE_CanSendSMS;
                        setText("发送验证码");
                        setClickable(true);
                        callback.onWalled();
                    } else {
                        int time = wallTime - nowTime;
                        if (time < 0) time = 0;
                        setText(time + "秒");
                        setClickable(false);
                        callback.onWalling(wallTime, nowTime, false);
                    }
                }
            }
        }));
        setText("发送验证码");
        callback.onWalled();
    }

    public void setLoading() {
        setClickable(false);//设置view不可点击
    }

    /**
     * 发送成功
     */
    public void onSendSuccess() {
        type = TYPE_NotSendSMS;//改变状态
        setClickable(false);//设置view不可点击
        nowTime = 0;//初始化时间
        setText(wallTime + "秒");
        callback.onWalling(wallTime, nowTime, true);
    }

    /**
     * 发送失败
     */
    public void onSendFail() {
        type = TYPE_CanSendSMS;//改变状态
        setClickable(true);//设置view可点击
        nowTime = 0;//初始化时间
        setText("发送验证码");
        callback.onWalled();
    }

    /**
     * 关闭
     * 不使用的时候必须调用
     */
    public void die() {
        threadSupport.timer_Cancel();
    }

    /**
     * 设置文字内容
     *
     * @param detail
     */
    private void setText(String detail) {
        if (tv != null)
            tv.setText(detail);
        else
            btn.setText(detail);
    }

    /**
     * 设置点击状态
     *
     * @param clickable
     */
    private void setClickable(boolean clickable) {
        if (tv != null)
            tv.setClickable(clickable);
        else
            btn.setClickable(clickable);
    }


    public interface OnSmsSupportCallback {
        /**
         * 1.未发送
         * 2.发送成功后过了等待时间，可发送下一个
         */
        public void onWalled();

        /**
         * 发送成功后开始倒计时
         */
        public void onWalling(int wallTime, int nowTime, boolean hasChangView);
    }
}
