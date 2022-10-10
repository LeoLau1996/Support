package com.leo.support.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.leo.support.R;
import com.leo.support.databinding.ActivityMainBinding;
import com.leo.support.info.AppPath;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.biz.MediaProjectionBiz;
import leo.work.support.biz.PermissionBiz;
import leo.work.support.biz.WorkFlow.CommonWorkFlowBiz;
import leo.work.support.biz.WorkFlow.WorkFlowControl;
import leo.work.support.biz.WorkFlow.WorkFlowTask;
import leo.work.support.support.toolSupport.LeoSupport;

public class MainActivity extends CommonActivity<ActivityMainBinding> {

    private PermissionBiz permissionBiz;
    private MediaProjectionBiz mediaProjectionBiz;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        LeoSupport.fullScreen(this, false);
        permissionBiz = new PermissionBiz(this);
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        permissionBiz.checkPermission(permissions, 10002, new PermissionBiz.OnPermissionBizCallBack() {
            @Override
            public void onPermissionSuccess(int requestCode, String[] permissions) {
            }

            @Override
            public void onPermissionFail(int requestCode, String[] successPermissions, String[] failPermissions) {

            }
        });
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnPlay.setOnClickListener(v -> {

        });
        binding.btnRecord.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return;
            }
            if (mediaProjectionBiz == null) {
                mediaProjectionBiz = new MediaProjectionBiz(this);
            }
            mediaProjectionBiz.start(String.format("%srecord_%s.h264", AppPath.getAppCache(), System.currentTimeMillis()), 720, 1280);
        });
    }
}
