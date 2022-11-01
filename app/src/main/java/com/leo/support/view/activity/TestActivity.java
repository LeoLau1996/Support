package com.leo.support.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.leo.support.R;
import com.leo.support.databinding.ActivityTestBinding;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.util.JumpUtil;


/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/31
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TestActivity extends CommonActivity<ActivityTestBinding> {

    public static void go(Activity activity) {
        JumpUtil.go(activity, TestActivity.class);
    }

    /*********************
     *     全 局 变 量    *
     *********************/
    //.....

    /*********************
     *  生 命 周 期 方 法  *
     *********************/

    @Override
    protected int setLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData(Bundle bundle) {
        TestDialog.showTestDialog(activity, null);
        new Handler().postDelayed(() -> {
            Log.e("liu1031","关闭");
            finish();
        }, 5000);
    }

    @Override
    protected void initViews(Bundle bundle) {
        //activity.getLifecycle().addObserver();



    }

    /*********************
     *     业 务 方 法    *
     *********************/
    //.....

}