package leo.work.support.MVP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.Support.MVPSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-08-05
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class BaseMVPFragmentActivity<T extends BaseView> extends FragmentActivity implements BaseMVPListener {
    protected T mView;
    public Context context;
    public FragmentManager mFragmentManager;
    public Activity activity;
    //数据
    public boolean isLoading = false;//是否正在加载
    public boolean hasFront = false;//当前页面是否在前台
    public String mFragmentTAG = "";
    public Fragment mFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activity = this;
        try {
            mView = getViewClass().newInstance();
        } catch (Exception e) {
            mView = null;
            throw new RuntimeException(e.toString());
        }
        setContentView(mView.create(getLayoutInflater(), null, this));
        mFragmentManager = getSupportFragmentManager();

        initData();
        mView.initData();
        mView.initView();
        mView.initListener();
        loadData(true, false);
    }

    protected abstract void initData();

    protected abstract void loadData(final boolean isShowState, final boolean isSaveLastData);

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


    public Class<T> getViewClass() {
        return MVPSupport.getViewClass(getClass());
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
