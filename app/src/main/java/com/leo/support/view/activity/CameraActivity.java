package com.leo.support.view.activity;

import android.os.Bundle;
import android.view.View;

import com.leo.support.R;
import com.leo.support.databinding.ActivityCameraBinding;

import leo.work.support.base.activity.CommonActivity;

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
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mLocalSurfaceView.startRecording();
            }
        });
        binding.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mLocalSurfaceView.stopRecord();
            }
        });
    }
}
