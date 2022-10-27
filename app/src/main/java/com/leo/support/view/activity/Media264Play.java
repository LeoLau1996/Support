package com.leo.support.view.activity;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;

import leo.work.support.support.file.FileSupport;

public class Media264Play implements Runnable {

    // xxx
    private String path;
    // xxx
    private Surface surface;
    // 编解码
    private MediaCodec mediaCodec;
    private MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

    private static final String TAG = Media264Play.class.getSimpleName();

    public Media264Play(Surface surface) {
        this("", surface);
    }

    public Media264Play(String path, Surface surface) {
        this.path = path;
        this.surface = surface;
        initMediaCodec();
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

    // 初始化编解码器
    private void initMediaCodec() {
        try {
            // 解码器
            mediaCodec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            // 创建视频格式 这里的宽高设置错误其实也没关系，解码器会从sps中解析到真实数据，但是如果sps解析异常 这个参数就非常重要
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, 1080, 1920);
            // 设置解码帧数
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 20);
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 1080 * 1920);
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
            // 设置编解码器配置信息
            // crypto 加密的意思  不需要加密直接传null
            mediaCodec.configure(mediaFormat, surface, null, 0);
            // 编码器开始工作
            mediaCodec.start();
            Log.e(TAG, "initMediaCodec    end");
        } catch (IOException e) {
            Log.e(TAG, String.format("IOException    %s", e.getMessage()));
        }
    }

    // 开启异步线程
    public void play() {
        new Thread(this).start();
    }

    // 播放
    public void play(ByteBuffer byteBuffer) {
        // 获取输入队列下标，最多等待100毫秒
        int inputBufferIndex = mediaCodec.dequeueInputBuffer(0);
        if (inputBufferIndex >= 0) {
            // 填数据
            ByteBuffer inputBuffer = mediaCodec.getInputBuffer(inputBufferIndex);
            inputBuffer.clear();
            byte[] data = new byte[byteBuffer.remaining()];
            byteBuffer.get(data);
            inputBuffer.put(data);

            // 入队
            mediaCodec.queueInputBuffer(inputBufferIndex, 0, data.length, System.currentTimeMillis(), 0);
        }


        // 获取输出队列下标，最多等待100毫秒
        for (int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0); outputBufferIndex >= 0; outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0)) {
            mediaCodec.releaseOutputBuffer(outputBufferIndex, true);
        }
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

    public static String byteToHexString(Byte b) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder();
        /**
         * 为什么要与0xof运算 因为0xof的二进制是0000 1111，同时1&运算得出原数，0&运算得出0
         */
        // 1.取出字节b的高四位的数值并追加
        // 把高四位向右移四位，与 0x0f运算得出高四位的数值
        sb.append(HEX.charAt((b >> 4) & 0x0f));
        // 2.取出低四位的值并追加
        // 直接与 0x0f运算得出低四位的数值
        sb.append(HEX.charAt(b & 0x0f));
        return sb.toString();
    }

}
