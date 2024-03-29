package com.leo.support.view.activity;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.surgery.scalpel.support.file.FileSupport;

public class Media264Play implements Runnable {

    // xxx
    private String path;
    // 编解码
    private MediaCodec mediaCodec;
    private MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

    private static final String TAG = Media264Play.class.getSimpleName();

    public Media264Play(Surface surface) {
        this("", surface, 1080, 1920);
    }

    public Media264Play(String path, Surface surface, int width, int height) {
        this.path = path;
        try {
            // 解码器
            mediaCodec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            // 创建视频格式 这里的宽高设置错误其实也没关系，解码器会从sps中解析到真实数据，但是如果sps解析异常 这个参数就非常重要
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height);
            // 设置解码帧数
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 20);
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 1080 * 1920);
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
            // 设置编解码器配置信息
            // crypto 加密的意思  不需要加密直接传null
            mediaCodec.configure(mediaFormat, surface, null, 0);
            // 编码器开始工作
            mediaCodec.start();
        } catch (IOException e) {
            Log.e(TAG, String.format("IOException    %s", e.getMessage()));
        }
    }

    @Override
    public void run() {
        byte[] bytes = FileSupport.readFileToByteArray(path);
        if (bytes == null) {
            Log.e(TAG, "读取数据失败");
            return;
        }
        Log.e(TAG, String.format("bytes %s", bytes.length));
        //
        int startIndex = 0;
        while (true) {
            // 找下标
            int nextIndex = findNextFrameIndex(startIndex + 2, bytes);
            if (nextIndex == -1) {
                Log.e(TAG, "nextIndex == -1");
                return;
            }
            // 获取输入队列下标，最多等待100毫秒
            int index = mediaCodec.dequeueInputBuffer(10 * 1000);
            if (index < 0) {
                continue;
            }
            // 填数据
            ByteBuffer byteBuffer = mediaCodec.getInputBuffer(index);
            int length = nextIndex - startIndex;
            Log.e(TAG, String.format("index    %s    startIndex = %s    nextIndex = %s    length = %s", index, startIndex, nextIndex, length));
            byteBuffer.put(bytes, startIndex, length);

            // 入队
            mediaCodec.queueInputBuffer(index, 0, length, 0, 0);

            startIndex = nextIndex;

            // 获取输出队列下标，最多等待100毫秒
            int dequeueOutputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 10 * 1000);
            if (dequeueOutputBufferIndex < 0) {
                continue;
            }
            // xxxx
            mediaCodec.releaseOutputBuffer(dequeueOutputBufferIndex, true);

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {

            }
        }
    }

    // 开启异步线程
    public void play() {
        new Thread(this).start();
    }

    // 播放
    public void play(ByteBuffer byteBuffer) {
        try {
            // 获取输入队列下标，最多等待100毫秒
            int inputBufferIndex = mediaCodec.dequeueInputBuffer(15000);
            if (inputBufferIndex >= 0) {
                // 填数据
                ByteBuffer inputBuffer = mediaCodec.getInputBuffer(inputBufferIndex);
                inputBuffer.clear();
                byte[] data = new byte[byteBuffer.remaining()];
                byteBuffer.get(data);
                inputBuffer.put(data);

                // 入队
                mediaCodec.queueInputBuffer(inputBufferIndex, 1, data.length - 1, System.currentTimeMillis(), 0);
            }


            // 获取输出队列下标，最多等待100毫秒
            for (int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0); outputBufferIndex >= 0; outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0)) {
                mediaCodec.releaseOutputBuffer(outputBufferIndex, true);
            }
        } catch (Exception exception) {
            Log.e(TAG, "play    Exception:" + exception.getMessage());
        }
    }

    // 释放
    public void release() {
        mediaCodec.release();
    }

    // 获取帧数
    private int findNextFrameIndex(int startIndex, byte[] data) {
        for (int i = startIndex; i < data.length - 4; i++) {
            if (data[i] == 0x00 && data[i + 1] == 0x00 && data[i + 2] == 0x01) {
                return i;
            }
            if (data[i] == 0x00 && data[i + 1] == 0x00 && data[i + 2] == 0x00 && data[i + 3] == 0x01) {
                return i;
            }
        }
        return -1;
    }
}
