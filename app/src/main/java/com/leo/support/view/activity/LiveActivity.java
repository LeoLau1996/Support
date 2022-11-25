package com.leo.support.view.activity;

import static com.surgery.scalpel.biz.MediaProjectionService.SOCKE_TYPE_SERVICE;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.leo.support.R;
import com.leo.support.databinding.ActivityLiveBinding;

import com.surgery.scalpel.base.activity.CommonActivity;
import com.surgery.scalpel.biz.AudioRecordBiz;
import com.surgery.scalpel.biz.AudioTrackBiz;
import com.surgery.scalpel.biz.MediaProjectionBiz;
import com.surgery.scalpel.biz.MediaProjectionService;
import com.surgery.scalpel.util.A2BSupport;
import com.surgery.scalpel.util.JumpUtil;
import com.surgery.scalpel.util.SocketUtils;


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
    private AudioRecordBiz audioRecordBiz;
    private AudioTrackBiz audioTrackBiz;
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
        socketType = getIntent().getIntExtra("socketType", SOCKE_TYPE_SERVICE);


        // 开始屏幕录制
        if (mediaProjectionBiz == null) {
            mediaProjectionBiz = new MediaProjectionBiz(this);
        }
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mediaProjectionBiz.start(socketType, null, 720, 1280, metrics.densityDpi);

        // 开始录音
        if (audioRecordBiz == null) {
            audioRecordBiz = new AudioRecordBiz(this);
        }
        audioRecordBiz.startRecord(data -> {
            byte[] newData = new byte[data.length + 1];
            newData[0] = MediaProjectionService.TYPE_AUDIO;
            System.arraycopy(data, 0, newData, 1, data.length);
            if (socketType == SOCKE_TYPE_SERVICE) {
                SocketUtils.getInstance().serverSend(newData);
            } else {
                SocketUtils.getInstance().clientSend(newData);
            }
        });
        // 初始化Socket
        if (socketType == SOCKE_TYPE_SERVICE) {
            SocketUtils.getInstance().openWebSocket(A2BSupport.String2int("8081"), byteBuffer -> {
                int type = byteBuffer.get(0);
                // 视频
                if (type == MediaProjectionService.TYPE_VIDEO) {
                    if (media264Play == null) {
                        media264Play = new Media264Play(surfaceHolder1.getSurface());
                    }
                    media264Play.play(byteBuffer);
                }
                // 音频
                else if (type == MediaProjectionService.TYPE_AUDIO) {
                    if (audioTrackBiz == null) {
                        audioTrackBiz = new AudioTrackBiz();
                    }
                    byte[] audioBytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(audioBytes);
                    audioTrackBiz.doPlay(audioBytes);
                }
            });
        } else {
            SocketUtils.getInstance().clientConnect("ws://192.168.31.41:8081", byteBuffer -> {
                int type = byteBuffer.get(0);
                // 视频
                if (type == MediaProjectionService.TYPE_VIDEO) {
                    if (media264Play == null) {
                        media264Play = new Media264Play(surfaceHolder1.getSurface());
                    }
                    media264Play.play(byteBuffer);
                }
                // 音频
                else if (type == MediaProjectionService.TYPE_AUDIO) {
                    if (media264Play == null) {
                        media264Play = new Media264Play(surfaceHolder1.getSurface());
                    }
                    media264Play.play(byteBuffer);
                }
            });
        }
    }

    @Override
    protected void initViews(Object data, int propertyId) {
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