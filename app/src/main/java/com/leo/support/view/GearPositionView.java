package com.leo.support.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.leo.support.R;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 档位
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class GearPositionView extends View {
    // View的宽高
    private int width, height;
    // 背景柱体宽度、上下间距
    private int bgWidth = 32, paddingVertical = 50;
    // 按钮宽高
    private int buttonWidth = 80, buttonHeight = 40;
    // 按钮位置
    private float y = paddingVertical;
    // 三个档位对应的Y点
    private float[] positions = new float[3];

    private Bitmap bitmap;
    private Rect dstRect;
    private Paint paint;

    public GearPositionView(Context context) {
        super(context);
    }

    public GearPositionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GearPositionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        positions[0] = paddingVertical;
        positions[1] = height / 2f;
        positions[2] = height - paddingVertical;

        {
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xxxx);
            }
            // 将bitmap绘制在屏幕的什么地方
            if (dstRect == null) {
                dstRect = new Rect();
            }

            int left = (width - bgWidth) / 2;
            dstRect.set(left, paddingVertical, left + bgWidth, height - paddingVertical);
            if (paint == null) {
                paint = new Paint();
            }
            canvas.drawBitmap(bitmap, null, dstRect, paint);
        }


        {
            // 颜色
            paint.setColor(Color.parseColor("#FFFF0000"));
            int left = (width - buttonWidth) / 2;
            float top = y - (buttonHeight / 2f);
            canvas.drawRoundRect(left, top, left + buttonWidth, top + buttonHeight, 20f, 20f, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {
                y = event.getY();
                if (y < paddingVertical) {
                    y = paddingVertical;
                } else if (y > (height - paddingVertical)) {
                    y = height - paddingVertical;
                }
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                //
                y = event.getY();
                if (y < paddingVertical) {
                    y = paddingVertical;
                } else if (y > (height - paddingVertical)) {
                    y = height - paddingVertical;
                }

                int index = -1;
                float min = 0;
                for (int i = 0; i < positions.length; i++) {
                    float result = Math.abs(positions[i] - y);
                    if (index == -1 || result < min) {
                        index = i;
                        min = result;
                    }
                }

                y = positions[index];
                invalidate();
                break;
            }
        }
        return true;
    }
}
