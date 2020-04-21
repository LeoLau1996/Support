package leo.work.support.Base.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.Support.Common.LogUtil;


public abstract class BaseFragmentActivity extends FragmentActivity {

    public Context context;
    public Activity activity;
    public FragmentManager mFragmentManager;
    public Fragment mFragment = null;

    //数据
    public boolean isLoading = false;//是否正在加载
    public boolean hasFront = false;//当前页面是否在前台
    public String mFragmentTAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("=======================>" + this.getClass().getName());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(setLayout());
        context = this;
        activity = this;
        mFragmentManager = getSupportFragmentManager();

        initData();

        initViews(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
        loadData();

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
    protected abstract void loadData();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

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
     * fragment切换逻辑
     *
     * @param fragment
     * @param tag
     */
    public void selectFragment(int id, Fragment fragment, String tag) {
        mFragmentTAG = tag;
        if (mFragment != fragment) {
            if (mFragment != null) {
                //隐藏当前的fragment
                mFragmentManager.beginTransaction().hide(mFragment).commitAllowingStateLoss();
            }
            //没有被添加  没有显示   没有删除
            if (!fragment.isAdded() && !fragment.isVisible() && !fragment.isRemoving()) {
                //添加fragment到Activity
                mFragmentManager.beginTransaction().add(id, fragment, mFragmentTAG).commitAllowingStateLoss();
            } else {
                //显示fragment到Activity
                mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
            }
            mFragment = fragment;
        }
    }

    //恢复Fragmen
    public void recoveryFragmet(String fragmentTAG) {

    }

}
