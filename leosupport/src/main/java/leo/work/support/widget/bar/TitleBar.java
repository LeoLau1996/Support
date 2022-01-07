package leo.work.support.widget.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import leo.work.support.R;
import leo.work.support.util.A2BSupport;
import leo.work.support.util.Get;
import leo.work.support.util.Is;
import leo.work.support.util.LogUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/1/7
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    //状态栏
    private View statusBar;
    //
    private RelativeLayout rlContent;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvMenu;
    private ImageView ivMenu;

    //状态栏高度
    private int statusBarHeight = 0;
    //内容高度
    private float contentHeight;
    //回调
    private OnTitleBarCallBack callBack;
    private int paddingLeft, paddingRight;

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        setPadding(0, getPaddingTop(), 0, getPaddingBottom());
        //获取属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleBar, 0, 0);
        //高度
        contentHeight = typedArray.getDimension(R.styleable.TitleBar_contentHeight, TitleBarDefaultInfo.getTitleBarDefaultInfo().getContentHeight());

        //初始化 ---- 状态栏
        initStatusBar(context, typedArray);
        //初始化 ---- 内容
        initContentView(context, typedArray);
        //初始化 ---- 返回键
        initIvBack(context, typedArray);
        //初始化 ---- 标题
        initTitle(context, typedArray);
        //初始化 ---- 图片菜单
        initIvMenu(context, typedArray);
        //初始化 ---- 文字菜单
        initTvMenu(context, typedArray);

        //添加
        addView(statusBar);
        addView(rlContent);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            //wrap_content
            case MeasureSpec.AT_MOST: {
                setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) (contentHeight + statusBarHeight));
                break;
            }
            //match_parent
            case MeasureSpec.EXACTLY:
            default: {
                break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0, roomViewCount = getChildCount(); i < roomViewCount; i++) {
            View childView = getChildAt(i);
            if (childView.getId() == R.id.statusBar) {

            } else if (childView.getId() == R.id.rlContent) {
                RelativeLayout rlContent = (RelativeLayout) childView;
                for (int index = 0, childCount = rlContent.getChildCount(); roomViewCount > 2 && index < childCount; index++) {
                    rlContent.getChildAt(index).setVisibility(GONE);
                }
            } else {
                LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + statusBarHeight, layoutParams.rightMargin, layoutParams.bottomMargin);
                childView.setLayoutParams(layoutParams);
                childView.setOnClickListener(this);
            }
        }

    }

    //初始化 ---- 状态栏
    private void initStatusBar(Context context, TypedArray typedArray) {
        //
        statusBar = new View(context);
        statusBar.setId(R.id.statusBar);
        //
        statusBar.setBackgroundColor(typedArray.getColor(R.styleable.TitleBar_statusBarColor, TitleBarDefaultInfo.getTitleBarDefaultInfo().getStatusBarColor()));
        //
        statusBarHeight = typedArray.getBoolean(R.styleable.TitleBar_showStatusBar, true) ? Get.getStatusBarHeight(context) : 0;
        //
        statusBar.setLayoutParams(new LayoutParams(Get.getWindowWidth(context), statusBarHeight));
    }

    //初始化 ---- 内容
    private void initContentView(Context context, TypedArray typedArray) {
        //
        rlContent = new RelativeLayout(context);
        //
        rlContent.setId(R.id.rlContent);
        //
        Drawable drawable = getBackground();
        if (drawable != null) {
            rlContent.setBackground(drawable);
        } else {
            rlContent.setBackgroundResource(TitleBarDefaultInfo.getTitleBarDefaultInfo().getContentBackground());
        }
        //
        LayoutParams layoutParams = new LayoutParams(Get.getWindowWidth(context), (int) contentHeight);
        layoutParams.addRule(RelativeLayout.BELOW, statusBar.getId());
        rlContent.setLayoutParams(layoutParams);

    }

    //初始化 ---- 返回键
    private void initIvBack(Context context, TypedArray typedArray) {
        //
        ivBack = new ImageView(context);
        //
        ivBack.setId(R.id.ivBack);
        //
        ivBack.setImageResource(typedArray.getResourceId(R.styleable.TitleBar_backImage, TitleBarDefaultInfo.getTitleBarDefaultInfo().getBackImage()));
        //
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(A2BSupport.dp2px(16), A2BSupport.dp2px(16));
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.setMarginStart(paddingLeft == 0 ? TitleBarDefaultInfo.getTitleBarDefaultInfo().getDefaultPadding() : paddingLeft);
        ivBack.setLayoutParams(layoutParams);
        //
        ivBack.setOnClickListener(this);
        //
        rlContent.addView(ivBack);
    }

    //初始化 ---- 标题
    private void initTitle(Context context, TypedArray typedArray) {
        //
        tvTitle = new TextView(context);
        //标题
        tvTitle.setText(typedArray.getString(R.styleable.TitleBar_title));
        //
        tvTitle.setTextColor(typedArray.getColor(R.styleable.TitleBar_titleColor, TitleBarDefaultInfo.getTitleBarDefaultInfo().getTitleColor()));
        //
        tvTitle.setTextSize(typedArray.getDimension(R.styleable.TitleBar_titleSize, 18));
        //
        tvTitle.setGravity(Gravity.CENTER);
        //
        tvTitle.setSingleLine(true);
        //
        tvTitle.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        //
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int titleGravity = typedArray.getInt(R.styleable.TitleBar_titleGravity, 0x01);
        if (titleGravity == 0x01) {
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (titleGravity == 0x02) {
            layoutParams.addRule(RelativeLayout.RIGHT_OF, ivBack.getId());
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        }
        tvTitle.setLayoutParams(layoutParams);
        //
        rlContent.addView(tvTitle);
    }

    //初始化 ---- 图片菜单
    private void initIvMenu(Context context, TypedArray typedArray) {
        //
        ivMenu = new ImageView(context);
        //
        int menuImage = typedArray.getResourceId(R.styleable.TitleBar_menuImage, 0);
        ivMenu.setImageResource(menuImage);
        //
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(A2BSupport.dp2px(16), A2BSupport.dp2px(16));
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.setMarginEnd(paddingRight == 0 ? TitleBarDefaultInfo.getTitleBarDefaultInfo().getDefaultPadding() : paddingRight);
        ivMenu.setLayoutParams(layoutParams);
        //
        ivMenu.setVisibility(menuImage != 0 ? VISIBLE : GONE);
        //
        ivMenu.setOnClickListener(this);
        //
        rlContent.addView(ivMenu);
    }

    //初始化 ---- 文字菜单
    private void initTvMenu(Context context, TypedArray typedArray) {
        //
        tvMenu = new TextView(context);
        //标题
        String text = typedArray.getString(R.styleable.TitleBar_menuText);
        tvMenu.setText(text);
        //
        tvMenu.setTextColor(typedArray.getColor(R.styleable.TitleBar_menuTextColor, Color.BLACK));
        //
        tvMenu.setTextSize(typedArray.getDimension(R.styleable.TitleBar_menuTextSize, 14));
        //
        tvMenu.setSingleLine(true);
        //
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.setMarginEnd(paddingRight == 0 ? TitleBarDefaultInfo.getTitleBarDefaultInfo().getDefaultPadding() : paddingRight);
        tvMenu.setLayoutParams(layoutParams);
        //
        tvMenu.setVisibility(!Is.isEmpty(text) ? VISIBLE : GONE);
        //
        tvMenu.setOnClickListener(this);
        //
        rlContent.addView(tvMenu);
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }
        if (ivBack != null && view.getId() == ivBack.getId() && callBack != null) {
            callBack.onClickBack();
        } else if (ivMenu != null && view.getId() == ivMenu.getId() && callBack != null) {
            callBack.onClickMenu();
        } else if (tvMenu != null && view.getId() == tvMenu.getId() && callBack != null) {
            callBack.onClickMenu();
        } else {
            callBack.onClickOtherView(view);
        }
    }

    //设置监听
    public void setCallBack(OnTitleBarCallBack callBack) {
        this.callBack = callBack;
    }

    public View getStatusBar() {
        return statusBar;
    }

    public RelativeLayout getRlContent() {
        return rlContent;
    }

    public ImageView getIvBack() {
        return ivBack;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvMenu() {
        return tvMenu;
    }

    public ImageView getIvMenu() {
        return ivMenu;
    }
}
