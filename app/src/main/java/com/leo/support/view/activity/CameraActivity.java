package com.leo.support.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.leo.support.R;
import com.leo.support.databinding.ActivityCameraBinding;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.support.toolSupport.LeoSupport;
import leo.work.support.util.CommonUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 相机
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/12
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * Camera 显示到 SurfaceView
 * ---------------------------------------------------------------------------------------------
 **/
public class CameraActivity extends CommonActivity<ActivityCameraBinding> {

    @Override
    protected int setLayout() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnStart.setOnClickListener(v -> {
            binding.mLocalSurfaceView.startRecording();
        });
        binding.btnStop.setOnClickListener(v -> {
            String path = binding.mLocalSurfaceView.stopRecord();
            CommonUtil.copy(activity, path);
            Log.e("liu1022", "path = " + path);
        });
    }
}
