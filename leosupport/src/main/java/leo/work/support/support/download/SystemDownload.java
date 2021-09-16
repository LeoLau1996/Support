package leo.work.support.support.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

import leo.work.support.base.activity.BaseActivity;
import leo.work.support.base.application.BaseApplication;
import leo.work.support.support.common.Get;
import leo.work.support.support.mediaPlayer.MediaPlayerSupport;

public class SystemDownload {


    private long downloadId;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (id != downloadId) {
                return;
            }
            callBack.onSuccess();
            destroy();

        }
    };
    private OnSystemDownloadCallBack callBack;

    public void download(String fileName, String url, OnSystemDownloadCallBack callBack) {
        download(Environment.DIRECTORY_DOWNLOADS, fileName, url, callBack);
    }

    public void download(String path, String fileName, String url, OnSystemDownloadCallBack callBack) {
        this.callBack = callBack;
        //
        registerReceiver();
        //
        DownloadManager downloadManager = (DownloadManager) BaseApplication.getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(new DownloadManager.Request(Uri.parse(url)).setDestinationInExternalPublicDir(path, fileName));
    }

    private void destroy() {
        callBack = null;
        if (broadcastReceiver != null) {
            BaseApplication.getApplication().unregisterReceiver(broadcastReceiver);
        }
        broadcastReceiver = null;
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BaseApplication.getApplication().registerReceiver(broadcastReceiver, intentFilter);
    }

    public interface OnSystemDownloadCallBack {
        void onSuccess();
    }

}
