package leo.work.support.Base.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.Support.Common.LogUtil;


public abstract class BaseActivity extends Activity {
    public Context context;
    public Activity activity;

    //数据
    public boolean isLoading = false;//是否正在加载
    public boolean hasFront = false;//当前页面是否在前台

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("=======================>" + this.getClass().getName());
        setContentView(setLayout());
        context = this;
        activity = this;
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


    //页面跳转
    public void jumpToActivity(Class mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
