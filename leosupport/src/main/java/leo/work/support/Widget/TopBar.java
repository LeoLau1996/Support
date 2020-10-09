package leo.work.support.Widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import leo.work.support.R;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/30
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/

public class TopBar extends RelativeLayout implements View.OnClickListener {

    public static TopBarDefaultInfo defaultInfo;

    /**
     * 返回键
     */
    private ImageView ivBack;
    private int backImage;
    private float backPaddingDP;


    /**
     * 标题
     */
    private TextView tvTitle;
    private String titleText;
    private int titleColor;
    private float titleSize;

    /**
     * 文字菜单键
     */
    private TextView tvMenu;
    private float menuTextPaddingDP;
    private String menuText;
    private int menuTextColor;
    private float menuTextSize;

    /**
     * 图片菜单键
     */
    private ImageView ivMenu;
    private int menuImage;
    private float menuImagePaddingDP;

    //高度
    private float heightDP;
    private int mWidth;
    private boolean showStatusBar;
    private int statusBarColor;
    private int statusBarHeight;
    //回调
    private OnTopBarCallBack callBack;

    public TopBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TopBar, 0, 0);
        //高度
        heightDP = typedArray.getDimension(R.styleable.TopBar_heightDP, 44);
        showStatusBar = typedArray.getBoolean(R.styleable.TopBar_showStatusBar, true);
        statusBarColor = typedArray.getColor(R.styleable.TopBar_statusBarColor, getDefaultInfo().getStatusBarColor());
        //返回键
        backImage = typedArray.getResourceId(R.styleable.TopBar_backImage, getDefaultInfo().getBackImage());
        backPaddingDP = typedArray.getDimension(R.styleable.TopBar_backPaddingDP, 14);
        //标题
        titleText = typedArray.getString(R.styleable.TopBar_titleText);
        titleColor = typedArray.getColor(R.styleable.TopBar_titleColor, getDefaultInfo().getTitleColor());
        titleSize = typedArray.getDimension(R.styleable.TopBar_titleSize, 16);
        //文字菜单
        menuTextPaddingDP = typedArray.getDimension(R.styleable.TopBar_menuTextPaddingDP, 20);
        menuText = typedArray.getString(R.styleable.TopBar_menuText);
        menuTextColor = typedArray.getColor(R.styleable.TopBar_menuTextColor, Color.BLACK);
        menuTextSize = typedArray.getDimension(R.styleable.TopBar_menuTextSize, 14);
        //图片菜单
        menuImage = typedArray.getResourceId(R.styleable.TopBar_menuImage, 0);
        menuImagePaddingDP = typedArray.getDimension(R.styleable.TopBar_menuImagePaddingDP, 14);
        typedArray.recycle();

        //顶部栏
        setPadding(0, showStatusBar ? getStatusBarHeight() : 0, 0, 0);
        /**
         * 返回键
         */
        {
            ivBack = new ImageView(context);
            ivBack.setPadding(dp2px(backPaddingDP), dp2px(backPaddingDP), dp2px(backPaddingDP), dp2px(backPaddingDP));
            if (backImage != 0) {
                ivBack.setImageResource(backImage);
            }
            LayoutParams layoutParams = new LayoutParams(dp2px(heightDP), dp2px(heightDP));
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            ivBack.setLayoutParams(layoutParams);
            ivBack.setOnClickListener(this);
            addView(ivBack);
        }

        /**
         * 标题
         */
        {
            tvTitle = new TextView(context);
            tvTitle.setText(titleText);
            tvTitle.setTextColor(titleColor);
            tvTitle.setTextSize(titleSize);
            tvTitle.setGravity(Gravity.CENTER);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, dp2px(heightDP));
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tvTitle.setLayoutParams(layoutParams);
            addView(tvTitle);
        }

        /**
         * 文字Menu
         */
        {
            tvMenu = new TextView(context);
            tvMenu.setText(menuText);
            tvMenu.setTextColor(menuTextColor);
            tvMenu.setTextSize(menuTextSize);
            tvMenu.setGravity(Gravity.CENTER);
            tvMenu.setPadding(dp2px(menuTextPaddingDP), 0, dp2px(menuTextPaddingDP), 0);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, dp2px(heightDP));
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tvMenu.setLayoutParams(layoutParams);
            tvMenu.setOnClickListener(this);
            tvMenu.setVisibility((menuText != null && !menuText.equals("")) ? VISIBLE : GONE);
            addView(tvMenu);
        }

        /**
         * 图片菜单
         */
        {
            ivMenu = new ImageView(context);
            ivMenu.setPadding(dp2px(menuImagePaddingDP), dp2px(menuImagePaddingDP), dp2px(menuImagePaddingDP), dp2px(menuImagePaddingDP));
            if (menuImage != 0) {
                ivMenu.setImageResource(menuImage);
            }
            LayoutParams layoutParams = new LayoutParams(dp2px(heightDP), dp2px(heightDP));
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            ivMenu.setLayoutParams(layoutParams);
            ivMenu.setOnClickListener(this);
            tvMenu.setVisibility(menuImage != 0 ? VISIBLE : GONE);
            addView(ivMenu);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("liu1009", "onMeasure");
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        //图标高度
        int height = dp2px(heightDP) + (showStatusBar ? getStatusBarHeight() : 0);
        setMeasuredDimension(width, height);
        if (width != 0) {
            mWidth = width;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("liu1009", "onDraw");
        if (showStatusBar) {
            Paint paint = new Paint();
            paint.setColor(statusBarColor);
            canvas.drawRect(0, 0, mWidth, getStatusBarHeight(), paint);
        }
    }


    private int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    @Override
    public void onClick(View view) {
        if (callBack == null) {
            return;
        }
        int id = view.getId();
        if (id == ivBack.getId()) {
            callBack.onClickBack();
        } else if (id == tvMenu.getId() || id == ivMenu.getId()) {
            callBack.onClickMenu();
        }
    }

    public void setCallBack(OnTopBarCallBack callBack) {
        this.callBack = callBack;
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

    public interface OnTopBarCallBack {
        void onClickBack();

        void onClickMenu();
    }


    private static TopBarDefaultInfo getDefaultInfo() {
        if (defaultInfo == null) {
            defaultInfo = new TopBarDefaultInfo(0, Color.BLACK, Color.TRANSPARENT);
        }
        return defaultInfo;
    }

    public static void setDefaultInfo(TopBarDefaultInfo defaultInfo) {
        TopBar.defaultInfo = defaultInfo;
    }

    public static class TopBarDefaultInfo {
        private int backImage;
        private int titleColor;
        private int statusBarColor;

        public TopBarDefaultInfo(int backImage, int titleColor, int statusBarColor) {
            this.backImage = backImage;
            this.titleColor = titleColor;
            this.statusBarColor = statusBarColor;
        }

        public int getBackImage() {
            return backImage;
        }

        public void setBackImage(int backImage) {
            this.backImage = backImage;
        }

        public int getTitleColor() {
            return titleColor;
        }

        public void setTitleColor(int titleColor) {
            this.titleColor = titleColor;
        }

        public int getStatusBarColor() {
            return statusBarColor;
        }

        public void setStatusBarColor(int statusBarColor) {
            this.statusBarColor = statusBarColor;
        }
    }


    //获取状态栏高度
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            Resources resources = getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = resources.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


}
