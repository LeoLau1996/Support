package com.leo.support.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.leo.support.R;
import com.leo.support.databinding.ActivityLiveBinding;
import com.leo.support.info.AppPath;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.biz.MediaProjectionBiz;
import leo.work.support.biz.MediaProjectionService;
import leo.work.support.util.A2BSupport;
import leo.work.support.util.JumpUtil;
import leo.work.support.util.SocketUtils;


/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/28
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注: String.format("%srecord_%s.h264", AppPath.getAppCache(), System.currentTimeMillis())
 * ---------------------------------------------------------------------------------------------
 **/
public class LiveActivity extends CommonActivity<ActivityLiveBinding> {

    public static void go(Activity activity, int socketType) {
        Bundle bundle = new Bundle();
        bundle.putInt("socketType", socketType);
        JumpUtil.go(activity, LiveActivity.class, bundle);
    }

    /*********************
     *     全 局 变 量    *
     *********************/


    public int socketType;
    // 屏幕录制
    private MediaProjectionBiz mediaProjectionBiz;
    private Media264Play media264Play;
    private SurfaceHolder surfaceHolder1, surfaceHolder2;

    /*********************
     *  生 命 周 期 方 法  *
     *********************/

    @Override
    protected int setLayout() {
        return R.layout.activity_live;
    }

    @Override
    protected void initData(Bundle bundle) {
        socketType = getIntent().getIntExtra("socketType", MediaProjectionService.SOCKE_TYPE_SERVICE);


        // 开始屏幕录制
        if (mediaProjectionBiz == null) {
            mediaProjectionBiz = new MediaProjectionBiz(this);
        }
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mediaProjectionBiz.start(socketType, null, 720, 1280, metrics.densityDpi);

        // 初始化Socket
        if (socketType == MediaProjectionService.SOCKE_TYPE_SERVICE) {
            SocketUtils.getInstance().openWebSocket(A2BSupport.String2int("8081"), byteBuffer -> {
                if (media264Play == null) {
                    media264Play = new Media264Play(surfaceHolder1.getSurface());
                }
                media264Play.play(byteBuffer);
            });
        } else {
            SocketUtils.getInstance().clientConnect("ws://192.168.0.185:8081", byteBuffer -> {
                if (media264Play == null) {
                    media264Play = new Media264Play(surfaceHolder1.getSurface());
                }
                media264Play.play(byteBuffer);
            });
        }
    }

    @Override
    protected void initViews(Bundle bundle) {
        binding.mSurfaceView1.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("liu1004", "mSurfaceView1    surfaceCreated");
                surfaceHolder1 = surfaceHolder;
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.e("liu1004", String.format("mSurfaceView1    surfaceChanged    %s %s %s", i, i1, i2));
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("liu1004", "mSurfaceView1    surfaceDestroyed");
                if (media264Play == null) {
                    return;
                }
                media264Play.release();
                media264Play = null;
            }
        });
        binding.mSurfaceView2.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("liu1004", "mSurfaceView2    surfaceCreated");
                surfaceHolder2 = surfaceHolder;
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.e("liu1004", String.format("mSurfaceView2    surfaceChanged    %s %s %s", i, i1, i2));
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("liu1004", "mSurfaceView2    surfaceDestroyed");
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaProjectionBiz == null) {
            return;
        }
        mediaProjectionBiz.stop();
    }

    /*********************
     *     业 务 方 法    *
     *********************/
    //.....

}