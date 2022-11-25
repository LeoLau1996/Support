package com.surgery.scalpel.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Leo on 2018/1/4.
 * 标题：正方形图片
 * 备注：
 * 脑图：
 * #         ┌─┐       ┌─┐
 * #      ┌──┘ ┴───────┘ ┴──┐
 * #      │                 │
 * #      │       ───       │
 * #      │  ─┬┘       └┬─  │
 * #      │                 │
 * #      │       ─┴─       │
 * #      │                 │
 * #      └───┐         ┌───┘
 * #          │         │
 * #          │         │
 * #          │         │
 * #          │         └──────────────┐
 * #          │                        │
 * #          │                        ├─┐
 * #          │                        ┌─┘
 * #          │                        │
 * #          └─┐  ┐  ┌───────┬──┐  ┌──┘
 * #            │ ─┤ ─┤       │ ─┤ ─┤
 * #            └──┴──┘       └──┴──┘
 * #                神兽保佑
 * #                代码无BUG!
 */

public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        //高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}