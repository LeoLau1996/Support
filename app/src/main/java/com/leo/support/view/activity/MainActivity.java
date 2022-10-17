package com.leo.support.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.leo.support.R;
import com.leo.support.databinding.ActivityMainBinding;
import com.leo.support.info.AppPath;
import com.leo.support.utils.SocketUtils;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.biz.MediaProjectionBiz;
import leo.work.support.biz.MediaProjectionService;
import leo.work.support.biz.PermissionBiz;
import leo.work.support.biz.WorkFlow.CommonWorkFlowBiz;
import leo.work.support.biz.WorkFlow.WorkFlowControl;
import leo.work.support.biz.WorkFlow.WorkFlowTask;
import leo.work.support.support.toolSupport.LeoSupport;
import leo.work.support.util.JumpUtil;

public class MainActivity extends CommonActivity<ActivityMainBinding> {

    private MediaProjectionBiz mediaProjectionBiz;
    private SurfaceHolder holder;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        LeoSupport.fullScreen(this, false);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initSurface();
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnPlay.setOnClickListener(v -> {
            String path = binding.etPath.getText().toString();
            path = "/storage/emulated/0/Android/data/com.leo.support/files/Download/case/camera_1665567722897.h264";
            Media264Play media264Play = new Media264Play(path, holder.getSurface());
            media264Play.play();
        });
        binding.btnRecord.setOnClickListener(v -> {
            if (mediaProjectionBiz == null) {
                mediaProjectionBiz = new MediaProjectionBiz(this);
            }
            mediaProjectionBiz.start(String.format("%srecord_%s.h264", AppPath.getAppCache(), System.currentTimeMillis()), 720, 1280);
        });
        binding.btnStopRecord.setOnClickListener(v -> {
            if (mediaProjectionBiz == null) {
                mediaProjectionBiz = new MediaProjectionBiz(this);
            }
            mediaProjectionBiz.stop();
        });
        binding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtil.go(activity, CameraActivity.class);
            }
        });
        binding.btnOpenSocket.setOnClickListener(v -> {
            SocketUtils.getInstance().openWebSocket();
        });
        binding.btnConnectSocket.setOnClickListener(v -> {
            SocketUtils.getInstance().connect();
        });
        binding.btnSendTest.setOnClickListener(v -> {
            byte[] bytes = {1, 2, 3};
            SocketUtils.getInstance().send(bytes);
        });
    }

    private void initSurface() {
        SurfaceHolder surfaceHolder = binding.mSurfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("liu1004", "surfaceCreated");
                holder = surfaceHolder;
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.e("liu1004", String.format("surfaceChanged    %s %s %s", i, i1, i2));
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("liu1004", "surfaceDestroyed");
            }
        });

    }
}
