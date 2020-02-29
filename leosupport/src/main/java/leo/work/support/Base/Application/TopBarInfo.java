package leo.work.support.Base.Application;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/17
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TopBarInfo {
    private int mBgColor;
    private int mBackIcon;
    private int mTitleColor;
    private int mMenuTextColor;

    public TopBarInfo(int mBgColor, int mBackIcon, int mTitleColor, int mMenuTextColor) {
        this.mBgColor = mBgColor;
        this.mBackIcon = mBackIcon;
        this.mTitleColor = mTitleColor;
        this.mMenuTextColor = mMenuTextColor;
    }

    public int getmBgColor() {
        return mBgColor;
    }

    public int getmBackIcon() {
        return mBackIcon;
    }

    public int getmTitleColor() {
        return mTitleColor;
    }

    public int getmMenuTextColor() {
        return mMenuTextColor;
    }
}
