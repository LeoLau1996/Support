package leo.work.support.biz;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import leo.work.support.base.application.BaseApplication;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 系统下载任务
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/18
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class SystemDownloadBiz {

    //下载ID
    private long downloadId;
    //下载回调
    private OnSystemDownloadBizCallBack callBack;
    //下载状态 ---- 广播
    private BroadcastReceiver broadcastReceiver;


    public void download(String fileName, String url, OnSystemDownloadBizCallBack callBack) {
        download(Environment.DIRECTORY_DOWNLOADS, fileName, url, callBack);
    }

    public void download(String path, String fileName, String url, OnSystemDownloadBizCallBack callBack) {
        this.callBack = callBack;
        //
        registerReceiver();
        //
        DownloadManager downloadManager = (DownloadManager) BaseApplication.getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(new DownloadManager.Request(Uri.parse(url)).setDestinationInExternalPublicDir(path, fileName));
    }

    private void destroy() {
        callBack = null;
        unRegisterReceiver();
        broadcastReceiver = null;
    }

    private void registerReceiver() {
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
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
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BaseApplication.getApplication().registerReceiver(broadcastReceiver, intentFilter);
    }

    //
    private void unRegisterReceiver() {
        if (broadcastReceiver == null) {
            return;
        }
        BaseApplication.getApplication().unregisterReceiver(broadcastReceiver);
    }

    public interface OnSystemDownloadBizCallBack {
        void onSuccess();
    }

}
