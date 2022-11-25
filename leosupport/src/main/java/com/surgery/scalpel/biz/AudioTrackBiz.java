package com.surgery.scalpel.biz;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/28
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class AudioTrackBiz {

    private AudioTrack audioTrack;

    public void doPlay(byte[] bytes) {
        if (audioTrack == null) {
            // 音乐类型,扬声器播放
            int streamType = AudioManager.STREAM_MUSIC;
            // 录音时采用的采样频率,所有播放时同样的采样频率
            int sampleRate = 44100;
            // 单声道,和录音时设置的一样
            int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
            // 录音使用16bit,所有播放时同样采用该方式
            int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
            // 流模式
            int mode = AudioTrack.MODE_STREAM;
            // 计算最小buffer大小
            int minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);
            //构造AudioTrack  不能小于AudioTrack的最低要求，也不能小于我们每次读的大小
            audioTrack = new AudioTrack(streamType, sampleRate, channelConfig, audioFormat, minBufferSize, mode);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                audioTrack.setVolume(16f);
            }
            audioTrack.play();
        }
        audioTrack.write(bytes, 1, bytes.length - 1);
    }

    public void release() {
        audioTrack.release();
        audioTrack = null;
    }

}
