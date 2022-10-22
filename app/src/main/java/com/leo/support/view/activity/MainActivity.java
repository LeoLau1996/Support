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
import leo.work.support.util.A2BSupport;
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
        binding.etPath.setText(AppPath.getAppCache());
        binding.etPort.setText("9007");
        binding.etSocketAddress.setText("ws://192.168.0.183:9007");
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnMenu.setOnClickListener(v -> {
            binding.llManu.setVisibility(binding.llManu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });
        binding.btnPlay.setOnClickListener(v -> {
            String path = binding.etPath.getText().toString();
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
        binding.btnCamera.setOnClickListener(v -> {
            JumpUtil.go(activity, CameraActivity.class);
        });
        binding.btnOpenSocket.setOnClickListener(v -> {
            SocketUtils.getInstance().openWebSocket(A2BSupport.String2int(binding.etPort.getText().toString()), byteBuffer -> {
                if (media264Play == null) {
                    media264Play = new Media264Play(holder.getSurface());
                }
                media264Play.play(byteBuffer);
            });
        });
        binding.btnConnectSocket.setOnClickListener(v -> {
            SocketUtils.getInstance().connect(binding.etSocketAddress.getText().toString());
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
