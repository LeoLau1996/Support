package leo.work.support.base.activity.mvp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.util.LogUtil;


public abstract class CommonMVPActivity<V extends ActivityView, B extends ActivityBiz> extends CommonActivity {

    //Activity对象
    public Activity activity;
    //View对象
    public V activityView;
    //业务逻辑对象
    public B activityBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("=======================>" + this.getClass().getName());
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //
        activity = this;
        //
        activityView = createActivityView();
        //
        activityBiz = createActivityBiz();
        //初始化数据
        activityBiz.initData(savedInstanceState);
        //初始化View
        activityView.initView();
        //网络加载
        activityBiz.loadData(true, false);
        //监听加载
        activityView.initListener();
    }


    //View
    protected abstract V createActivityView();

    //逻辑
    protected abstract B createActivityBiz();

    @Override
    protected void onResume() {
        super.onResume();
        activityView.onResume();
        activityBiz.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityView.onStart();
        activityBiz.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityView.onPause();
        activityBiz.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityView.onStop();
        activityBiz.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityView.onDestroy();
        activityBiz.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        activityView.onRequestPermissionsResult(requestCode, permissions, grantResults);
        activityBiz.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        activityView.onActivityResult(requestCode, resultCode, data);
        activityBiz.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        activityView.onNewIntent(intent);
        activityBiz.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activityView.onBackPressed();
        activityBiz.onBackPressed();
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
