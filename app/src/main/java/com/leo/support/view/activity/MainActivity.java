package com.leo.support.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.NonNull;

import com.leo.support.R;
import com.leo.support.databinding.ActivityMainBinding;
import com.leo.support.info.AppPath;

import java.nio.ByteBuffer;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.biz.MediaProjectionBiz;
import leo.work.support.support.toolSupport.LeoSupport;
import leo.work.support.util.JumpUtil;
import leo.work.support.util.SocketUtils;

public class MainActivity extends CommonActivity<ActivityMainBinding> {

    private MediaProjectionBiz mediaProjectionBiz;
    private SurfaceHolder holder;
    private Media264Play media264Play;

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
            media264Play = new Media264Play(path, holder.getSurface());
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
            SocketUtils.getInstance().openWebSocket(9007, byteBuffer -> {
                if (media264Play == null) {
                    return;
                }
                media264Play.play(byteBuffer);
            });
        });
        binding.btnInit.setOnClickListener(v -> {
            if (media264Play != null) {
                return;
            }
            media264Play = new Media264Play(holder.getSurface());
        });
        binding.btnConnectSocket.setOnClickListener(v -> {
            SocketUtils.getInstance().connect("ws://192.168.31.24:9007");
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
