package leo.work.support.biz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;

import androidx.annotation.RequiresApi;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 录音
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/28
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class AudioRecordBiz extends CommonLifeBiz {

    private static final String TAG = MediaProjectionBiz.class.getSimpleName();
    // 采样率，现在能够保证在所有设备上使用的采样率是44100Hz, 但是其他的采样率（22050, 16000, 11025）在一些设备上也可以使用。
    public static final int SAMPLE_RATE_INHZ = 44100;
    // 声道数。CHANNEL_IN_MONO and CHANNEL_IN_STEREO. 其中CHANNEL_IN_MONO是可以保证在所有设备能够使用的。
    public static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    // 返回的音频数据的格式。 ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, and ENCODING_PCM_FLOAT.
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;


    private PermissionBiz permissionBiz;

    private volatile AudioRecord audioRecord;
    // 录制状态
    private volatile boolean isRecording;
    private volatile OnAudioRecordBizCallBack callBack;

    public AudioRecordBiz(LifeControlInterface lifeControlInterface) {
        super(lifeControlInterface);
    }

    public void startRecord(OnAudioRecordBizCallBack callBack) {
        if (isRecording) {
            return;
        }
        this.callBack = callBack;
        if (permissionBiz == null) {
            permissionBiz = new PermissionBiz(getLifeControlInterface());
        }
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
        permissionBiz.checkPermission(permissions, 10002, new PermissionBiz.OnPermissionBizCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionSuccess(int requestCode, String[] permissions) {
                recording();
            }

            @Override
            public void onPermissionFail(int requestCode, String[] successPermissions, String[] failPermissions) {

            }
        });
    }

    // 开始录制
    @SuppressLint("MissingPermission")
    private void recording() {
        if (isRecording) {
            return;
        }
        isRecording = true;
        new Thread(() -> {
            // 最小长度
            final int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT);
            // 初始化缓存
            final byte[] data = new byte[minBufferSize];
            // 初始化录音机
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize);
            audioRecord.startRecording();
            while (isRecording) {
                audioRecord.read(data, 0, minBufferSize);
                if (callBack != null) {
                    callBack.onRecordingResult(data);
                }
            }
            // 释放
            audioRecord.release();
            audioRecord = null;
        }).start();
    }

    // 停止录音
    public void endRecord() {
        isRecording = false;
        callBack = null;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public interface OnAudioRecordBizCallBack {

        // 录制结果（异步线程）
        void onRecordingResult(byte[] data);

    }

}
