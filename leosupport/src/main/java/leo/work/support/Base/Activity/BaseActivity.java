package leo.work.support.Base.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.Support.Common.Has;
import leo.work.support.Support.Common.LogUtil;
import leo.work.support.Support.ToolSupport.LeoSupport;


public abstract class BaseActivity extends Activity {
    public Context context;
    public Activity activity;

    //数据
    public boolean isLoading = false;//是否正在加载
    public boolean hasFront = false;//当前页面是否在前台

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
        initData();

        initViews(savedInstanceState);

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
    protected void loadData(){

    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BaseApplication.getApplication().onRestoreBiz();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
