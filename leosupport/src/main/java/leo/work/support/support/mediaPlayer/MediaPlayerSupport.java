package leo.work.support.support.mediaPlayer;

import android.media.MediaPlayer;

import leo.work.support.support.common.LogUtil;
import leo.work.support.support.download.DownloadSupport;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2020/7/23
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MediaPlayerSupport {

    private static MediaPlayerSupport instance;

    //获取实例
    public static synchronized MediaPlayerSupport getInstance() {
        if (instance == null) {
            instance = new MediaPlayerSupport();
        }
        return instance;
    }

    private MediaPlayer mMediaPlayer;
    private final String TAG = "MediaPlayerSupport";

    public MediaPlayerSupport() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                LogUtil.e(TAG, "播放完毕");
                mMediaPlayer.reset();
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                LogUtil.e(TAG, "播放错误     i = " + i + "    i1 = " + i1);
                return true;
            }
        });
    }

    public void play(String url, final String fileName, final String path) {
        new DownloadSupport().download(url, fileName, path, new DownloadSupport.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                play(path + fileName);
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFail() {
                LogUtil.e(TAG, "下载失败");
            }
        });
    }

    private void play(String path) {
        try {
            if (mMediaPlayer.isPlaying()) {
                stop();
            }
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            LogUtil.e(TAG, "play错误     e = " + e.getMessage());
        }
    }

    public void stop() {
        try {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        } catch (Exception e) {
            LogUtil.e(TAG, "stop错误     e = " + e.getMessage());
        }
    }
}
