package leo.work.support.base.activity.mvp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import leo.work.support.support.common.LogUtil;


public abstract class CommonMVPActivity<V extends ActivityView, B extends ActivityBiz> extends AppCompatActivity {

    public Activity activity;
    public V view;
    public B biz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("=======================>" + this.getClass().getName());
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activity = this;
        view = createViewModel();
        biz = createLogicModel();
        setContentView(view.layoutId);

        biz.initData(savedInstanceState);
        view.initView();
        biz.loadData(true, false);
        view.initListener();
    }


    //View
    protected abstract V createViewModel();

    //逻辑
    protected abstract B createLogicModel();

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
        biz.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        view.onStart();
        biz.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
        biz.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        view.onStop();
        biz.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.onDestroy();
        biz.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        view.onRequestPermissionsResult(requestCode, permissions, grantResults);
        biz.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        view.onActivityResult(requestCode, resultCode, data);
        biz.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        view.onNewIntent(intent);
        biz.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        view.onBackPressed();
        biz.onBackPressed();
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
