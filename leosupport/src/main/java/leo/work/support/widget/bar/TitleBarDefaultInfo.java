package leo.work.support.widget.bar;

import android.graphics.Color;

import leo.work.support.R;
import leo.work.support.util.A2BSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: TitleBar默认配置
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/1/7
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TitleBarDefaultInfo {

    public static TitleBarDefaultInfo titleBarDefaultInfo;

    public static TitleBarDefaultInfo getTitleBarDefaultInfo() {
        if (titleBarDefaultInfo == null) {
            titleBarDefaultInfo = new TitleBarDefaultInfo(A2BSupport.dp2px(14), 0, Color.BLACK, Color.TRANSPARENT, A2BSupport.dp2px(44), R.color.transp);
        }
        return titleBarDefaultInfo;
    }

    public static void setTitleBarDefaultInfo(TitleBarDefaultInfo titleBarDefaultInfo) {
        TitleBarDefaultInfo.titleBarDefaultInfo = titleBarDefaultInfo;
    }

    //返回键Id
    private int backImage;
    //默认间距
    private int defaultPadding;
    //标题颜色
    private int titleColor;
    //状态栏颜色
    private int statusBarColor;
    //高度
    private float contentHeight;
    //默认内容背景
    private int contentBackground;

    public TitleBarDefaultInfo(int defaultPadding, int backImage, int titleColor, int statusBarColor, float contentHeight, int contentBackground) {
        this.defaultPadding = defaultPadding;
        this.backImage = backImage;
        this.titleColor = titleColor;
        this.statusBarColor = statusBarColor;
        this.contentHeight = contentHeight;
        this.contentBackground = contentBackground;
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

    public float getContentHeight() {
        return contentHeight;
    }

    public void setContentHeight(float contentHeight) {
        this.contentHeight = contentHeight;
    }

    public int getDefaultPadding() {
        return defaultPadding;
    }

    public void setDefaultPadding(int defaultPadding) {
        this.defaultPadding = defaultPadding;
    }

    public int getContentBackground() {
        return contentBackground;
    }

    public void setContentBackground(int contentBackground) {
        this.contentBackground = contentBackground;
    }
}
