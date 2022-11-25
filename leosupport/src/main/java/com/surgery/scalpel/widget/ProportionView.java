package com.surgery.scalpel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.surgery.scalpel.R;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:基于LinearLayout的按比例控件
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-09-05
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:你可以根据你的需求设置宽高比
 * 默认以宽度为参照物
 * ---------------------------------------------------------------------------------------------
 **/
public class ProportionView extends View {
    private int x, y;//宽高
    private float  maxHeight;

    public ProportionView(Context context) {
        super(context);
        TypedArray array = context.obtainStyledAttributes(R.styleable.ProportionView);
        x = array.getInt(R.styleable.ProportionView_x, 1);
        y = array.getInt(R.styleable.ProportionView_y, 1);
        maxHeight = array.getDimension(R.styleable.ProportionView_maxHeight, -1);
    }

    public ProportionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProportionView);
        x = array.getInt(R.styleable.ProportionView_x, 1);
        y = array.getInt(R.styleable.ProportionView_y, 1);
        maxHeight = array.getDimension(R.styleable.ProportionView_maxHeight, -1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = (width * y) / x;

        if (maxHeight != -1 && height > maxHeight) {
            height = (int) maxHeight;
        }
        setMeasuredDimension(width, height);
    }
}
