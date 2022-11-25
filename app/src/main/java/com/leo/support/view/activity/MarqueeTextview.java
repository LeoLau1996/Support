package com.leo.support.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.leo.support.R;

import com.surgery.scalpel.util.A2BSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 跑马灯
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/11/9
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MarqueeTextview extends View {

    private String TAG = MarqueeTextview.class.getSimpleName();
    // 画笔
    private Paint paint;
    // 动画
    public ValueAnimator animator;
    // 进度
    private int maxValue = 1000;
    // 当前动画进度
    private int currentProgress;
    // 文字
    private String text = "测试文字1234567890测试文字1234567890测试文字1234567890测试文字1234567890";
    //private String text = "测试文字1234567890";
    // 帧数
    private int frames = 60;
    // 刷新间隔(毫秒)
    private int refreshInterval;
    // 上一次刷新时间
    private long lastInvalidateTimestamp;
    // 当前是否为重复播放
    private boolean repeat;
    // 回调监听
    private OnMarqueeTextviewCallBack callBack;
    private AnimatorListenerAdapter listener;

    public MarqueeTextview(Context context) {
        super(context);
        initData();
    }

    public MarqueeTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public MarqueeTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        // 初始化画笔
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.red));
        paint.setTextSize(A2BSupport.sp2px(18));

        refreshInterval = 1000 / frames;

        animator = ValueAnimator.ofInt(1000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (currentProgress == (int) animation.getAnimatedValue()) {
                    return;
                }
                currentProgress = (int) animation.getAnimatedValue();
                Log.i(TAG, String.format("onAnimationUpdate    currentProgress = %s", currentProgress));
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - lastInvalidateTimestamp >= refreshInterval) {
                    invalidate();
                }
            }
        });
        listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                Log.e(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.e(TAG, "onAnimationEnd");
                if (callBack != null) {
                    callBack.onPlayEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                repeat = true;
                super.onAnimationRepeat(animation);
                Log.e(TAG, "onAnimationRepeat");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.e(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
                Log.e(TAG, "onAnimationPause");
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
                Log.e(TAG, "onAnimationResume");
            }
        };
        animator.addListener(listener);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10 * 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        // View的高度
        float height = getHeight();
        // 文字总宽度
        float measureTextWidth = paint.measureText(text);
        float x = 0;
        //  当前文字长度 > 屏幕宽度
        if (measureTextWidth > width) {
            if (repeat) {
                measureTextWidth += width;
            }
            // 单位宽度
            float unitWidth = measureTextWidth / maxValue;
            Log.i(TAG, String.format("width = %s    height = %s    measureText = %s    unitWidth = %s", width, height, measureTextWidth, unitWidth));
            if (repeat) {
                x = width - (currentProgress * unitWidth);
            } else {
                x = -(currentProgress * unitWidth);
            }
        }
        canvas.drawText(text, x, (height / 2) + getTextBaseline(text), paint);

        // 时间戳
        lastInvalidateTimestamp = System.currentTimeMillis();
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(@ColorInt int color) {
        if (paint == null) {
            return;
        }
        paint.setColor(color);
    }

    public void setTextSize(int sp) {
        if (paint == null) {
            return;
        }
        paint.setTextSize(sp2px(sp));
    }

    public void setTextStyle(Typeface typeface) {
        if (paint == null) {
            return;
        }
        paint.setTypeface(typeface);
    }

    // 开始播发
    public void start(int playCount) {
        if (playCount > 1) {
            animator.setRepeatCount(playCount - 1);
        } else {
            animator.setRepeatCount(0);
        }
        start();
    }

    private void start() {
        repeat = false;
        animator.setIntValues(0, 1000);
        animator.start();
    }

    // 停止
    public void stop() {

    }

    public void setCallBack(OnMarqueeTextviewCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnMarqueeTextviewCallBack {

        void onPlayEnd();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator == null) {
            return;
        }
        animator.cancel();
        animator.end();
        animator.removeAllUpdateListeners();
        animator.removeListener(listener);
        listener = null;
    }


    private int getTextBaseline(String text) {
        Rect boundRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), boundRect);
        Paint.FontMetricsInt metricsInt = paint.getFontMetricsInt();
        return (metricsInt.descent - metricsInt.ascent) / 2 - metricsInt.descent;
    }

    private int sp2px(float sp) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
