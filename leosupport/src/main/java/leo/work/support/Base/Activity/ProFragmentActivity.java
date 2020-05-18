package leo.work.support.Base.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;

import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.R;
import leo.work.support.Support.Common.Has;
import leo.work.support.Support.Common.LogUtil;


public abstract class ProFragmentActivity extends FragmentActivity {

    public Context context;
    public Activity activity;
    public FragmentManager mFragmentManager;
    public Fragment mFragment = null;

    //数据
    public boolean isLoading = false;//是否正在加载
    public boolean hasFront = false;//当前页面是否在前台
    public String mFragmentTAG = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //支持转场动画
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        LogUtil.e("=======================>" + this.getClass().getName());
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(setLayout());
        context = this;
        activity = this;
        mFragmentManager = getSupportFragmentManager();
        initData();

        initViews(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
        loadData(true, false);
        initListener();
    }

    protected abstract int setLayout();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 加载View
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 加载数据，如：网络请求
     */
    protected abstract void loadData(final boolean isShowState, final boolean isSaveLastData);

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    //1.持有碎片的MainActivity异常崩溃 2.App进入后台,系统内存不足被回收
    //避免碎片布局重叠
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null && !mFragmentTAG.equals("")) {
            if (mFragment != null) {
                //隐藏当前的fragment,避免重叠
                mFragmentManager.beginTransaction().hide(mFragment).commitAllowingStateLoss();
                mFragment = null;
            }
            outState.putString("currentTab", mFragmentTAG);
        }
        super.onSaveInstanceState(outState);
    }

    //只有在activity确实是被系统回收，重新创建activity的情况下才会被调用。
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (!mFragmentTAG.equals("")) {
            if (savedInstanceState != null) {
                //隐藏碎片 避免重叠
                if (mFragment != null) {
                    //隐藏当前的fragment
                    mFragmentManager.beginTransaction().hide(mFragment).commitAllowingStateLoss();
                    mFragment = null;
                }
                recoveryFragmet(savedInstanceState.getString("currentTab"));
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }


    /**
     * 使用时应该写在这上面
     * ....
     * super.onResume();
     * 不应该写在super下面
     */
    @Override
    protected void onResume() {
        super.onResume();
        hasFront = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        hasFront = false;
    }

    /**
     * fragment切换逻辑
     *
     * @param fragment
     * @param index
     */
    public void selectFragment(int id, Fragment fragment, int index) {
        //如果相同
        if (mFragment == fragment) {
            return;
        }

        //隐藏当前的fragment
        if (mFragment != null) {
            mFragmentManager.beginTransaction()
                    //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .hide(mFragment).commitAllowingStateLoss();
        }
        //没有被添加  没有显示   没有删除 ---->   添加新的Fragment
        if (!fragment.isAdded() && !fragment.isVisible() && !fragment.isRemoving()) {
            //添加fragment到Activity
            mFragmentManager.beginTransaction()
                    //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(id, fragment, String.valueOf(index)).commitAllowingStateLoss();
        }
        //显示fragment
        else {
            mFragmentManager.beginTransaction()
                    //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .show(fragment).commitAllowingStateLoss();
        }
        mFragment = fragment;
        mFragmentTAG = String.valueOf(index);
    }

    public void selectFragmentAnimation(int id, Fragment fragment, int index) {
        //如果相同
        if (mFragment == fragment) {
            return;
        }
        int var1, var2;
        int var3, var4;
        if (index > Integer.valueOf(mFragmentTAG)) {
            var1 = R.anim.from_right;
            var2 = R.anim.out_left;


            var3 = R.anim.from_left;
            var4 = R.anim.out_right;
        } else {
            var1 = R.anim.from_left;
            var2 = R.anim.out_right;


            var3 = R.anim.from_right;
            var4 = R.anim.out_left;
        }


        //隐藏当前的fragment
        if (mFragment != null) {
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(var1, var2)
                    .hide(mFragment).commitAllowingStateLoss();
        }
        //没有被添加  没有显示   没有删除 ---->   添加新的Fragment
        if (!fragment.isAdded() && !fragment.isVisible() && !fragment.isRemoving()) {
            //添加fragment到Activity
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(var3, var4)
                    .add(id, fragment, String.valueOf(index)).commitAllowingStateLoss();
        }
        //显示fragment
        else {
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(var3, var4)
                    .show(fragment).commitAllowingStateLoss();
        }
        mFragment = fragment;
        mFragmentTAG = String.valueOf(index);
    }

    //恢复Fragmen
    public void recoveryFragmet(String fragmentTAG) {

    }
}
