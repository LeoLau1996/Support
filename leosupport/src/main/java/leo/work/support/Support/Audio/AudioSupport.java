package leo.work.support.Support.Audio;

import android.media.AudioManager;
import android.media.MediaPlayer;

import org.greenrobot.eventbus.EventBus;

import leo.work.support.Base.Service.BaseService;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/27
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class AudioSupport extends BaseService implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private MediaPlayer mMediaPlay;

    @Override
    protected void initData() {
        mMediaPlay = new MediaPlayer();
        mMediaPlay.setOnCompletionListener(this);
        mMediaPlay.setOnErrorListener(this);
        mMediaPlay.setOnPreparedListener(this);
        mMediaPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void initBiz() {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }


    @Override
    protected void SendToService(Object... obj) {

    }

    @Override
    protected void unBind() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void serviceDie() {

    }
}
