package leo.work.support.widget.bar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import leo.work.support.R;
import leo.work.support.util.A2BSupport;
import leo.work.support.util.Get;
import leo.work.support.util.Is;

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
    //标题位置
    private int titleGravity;
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
            //状态栏
            if (childView.getId() == R.id.titleBarStatusBar) {

            }
            //内容栏
            else if (childView.getId() == R.id.rlTitleBarContent) {
                RelativeLayout rlContent = (RelativeLayout) childView;
                for (int index = 0, childCount = rlContent.getChildCount(); roomViewCount > 2 && index < childCount; index++) {
                    rlContent.getChildAt(index).setVisibility(GONE);
                }
            }
            //其他子View
            else {
                removeView(childView);
                rlContent.addView(childView);
            }
        }

    }

    //初始化 ---- 状态栏
    private void initStatusBar(Context context, TypedArray typedArray) {
        //
        statusBar = new View(context);
        statusBar.setId(R.id.titleBarStatusBar);
        //
        statusBar.setBackgroundColor(typedArray.getColor(R.styleable.TitleBar_statusBarColor, context.getResources().getColor(TitleBarDefaultInfo.getTitleBarDefaultInfo().getStatusBarColor())));
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
        rlContent.setId(R.id.rlTitleBarContent);
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
        rlContent.setPadding(
                paddingLeft == 0 ? TitleBarDefaultInfo.getTitleBarDefaultInfo().getDefaultPadding() : paddingLeft,
                rlContent.getPaddingTop(),
                paddingRight == 0 ? TitleBarDefaultInfo.getTitleBarDefaultInfo().getDefaultPadding() : paddingRight,
                rlContent.getPaddingBottom());
        rlContent.setLayoutParams(layoutParams);

    }

    //初始化 ---- 返回键
    private void initIvBack(Context context, TypedArray typedArray) {
        //
        ivBack = new ImageView(context);
        //
        ivBack.setId(R.id.ivTitleBarBack);
        //
        ivBack.setImageResource(typedArray.getResourceId(R.styleable.TitleBar_backImage, TitleBarDefaultInfo.getTitleBarDefaultInfo().getBackImage()));
        //
        int backImageSize = (int) typedArray.getDimension(R.styleable.TitleBar_backImageSize, A2BSupport.dp2px(16));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(backImageSize, backImageSize);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
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
        //
        tvTitle.setId(R.id.tvTitleBarTitle);
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
        titleGravity = typedArray.getInt(R.styleable.TitleBar_titleGravity, 0x01);
        if (titleGravity == 0x01) {
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else if (titleGravity == 0x02) {
            layoutParams.addRule(RelativeLayout.RIGHT_OF, ivBack.getId());
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            tvTitle.setPadding((int) typedArray.getDimension(R.styleable.TitleBar_titlePadding, 0), tvTitle.getPaddingTop(), tvTitle.getPaddingRight(), tvTitle.getPaddingBottom());
        }
        tvTitle.setLayoutParams(layoutParams);
        //
        tvTitle.setOnClickListener(this);
        //
        rlContent.addView(tvTitle);
    }

    //初始化 ---- 图片菜单
    private void initIvMenu(Context context, TypedArray typedArray) {
        //
        ivMenu = new ImageView(context);
        //
        ivMenu.setId(R.id.ivTitleBarMenu);
        //
        int menuImage = typedArray.getResourceId(R.styleable.TitleBar_menuImage, 0);
        ivMenu.setImageResource(menuImage);
        //
        int menuImageSize = (int) typedArray.getDimension(R.styleable.TitleBar_menuImageSize, A2BSupport.dp2px(16));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(menuImage == 0 ? 0 : menuImageSize, menuImage == 0 ? 0 : menuImageSize);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ivMenu.setLayoutParams(layoutParams);
        //
        ivMenu.setVisibility(menuImage != 0 ? VISIBLE : INVISIBLE);
        //
        ivMenu.setOnClickListener(this);
        //
        rlContent.addView(ivMenu);
    }

    //初始化 ---- 文字菜单
    private void initTvMenu(Context context, TypedArray typedArray) {
        //
        tvMenu = new TextView(context);
        //设置ID
        tvMenu.setId(R.id.tvTitleBarMenu);
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
        layoutParams.addRule(RelativeLayout.LEFT_OF, ivMenu.getId());
        layoutParams.setMarginEnd((int) typedArray.getDimension(R.styleable.TitleBar_menuPadding, 0));
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
        if (view.getId() == R.id.ivTitleBarBack) {
            if (callBack != null) {
                callBack.onClickBack();
            } else if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            }
        } else if (view.getId() == R.id.ivTitleBarMenu) {
            if (callBack != null) {
                callBack.onClickMenu();
            }
        } else if (view.getId() == R.id.tvTitleBarMenu) {
            if (callBack != null) {
                callBack.onClickMenu();
            }
        } else if (view.getId() == R.id.tvTitleBarTitle) {
            //返回
            if (titleGravity == 0x02) {
                if (callBack != null) {
                    callBack.onClickBack();
                } else {
                    if (getContext() instanceof Activity) {
                        ((Activity) getContext()).finish();
                    }
                }
                return;
            }
            if (callBack != null) {
                callBack.onClickOtherView(view);
            }
        } else {
            if (callBack != null) {
                callBack.onClickOtherView(view);
            }
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
