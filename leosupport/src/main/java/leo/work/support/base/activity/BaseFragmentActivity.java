package leo.work.support.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.view.Window;

import leo.work.support.base.application.BaseApplication;
import leo.work.support.R;
import leo.work.support.support.common.LogUtil;


public abstract class BaseFragmentActivity extends FragmentActivity {

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

        initData(savedInstanceState);

        initViews(savedInstanceState);

        loadData();

        initListener();
    }

    protected abstract int setLayout();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 加载View
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 加载数据，如：网络请求
     */
    protected void loadData() {

    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

    }

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
        BaseApplication.getApplication().onRestoreBiz();
        super.onRestoreInstanceState(savedInstanceState);
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


    //恢复Fragmen
    public void recoveryFragmet(String fragmentTAG) {

    }

    /**
     * 设置 app 不随着系统字体的调整而变化
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return resources;
    }
}
