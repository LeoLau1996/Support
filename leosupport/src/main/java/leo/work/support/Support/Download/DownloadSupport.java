package leo.work.support.Support.Download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import leo.work.support.Support.File.FileSupport;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载支持
 * Created by 刘桂安 on 2018/6/22.
 */

public class DownloadSupport {
    private Context context;
    private long downloadId;
    private OnServiceDownloadCallBack onServiceDownloadCallBack;
    private OnDownloadListener onDownloadListener;
    final int OnFail = 0, OnDownloading = 1, OnDownloadSuccess = 2;


    //系统自带下载API
    public DownloadSupport(Context mContext, String fileName, String url, OnServiceDownloadCallBack callBack) {
        this.context = mContext;
        this.onServiceDownloadCallBack = callBack;
        /**
         * 注册广播
         */
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);//注册频道
        context.registerReceiver(broadcastReceiver, myIntentFilter);
        /**
         * 开始下载
         */
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);
    }

    //系统自带下载API
    public DownloadSupport(Context mContext, String path, String fileName, String url, OnServiceDownloadCallBack callBack) {
        this.context = mContext;
        this.onServiceDownloadCallBack = callBack;
        /**
         * 注册广播
         */
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);//注册频道
        context.registerReceiver(broadcastReceiver, myIntentFilter);
        /**
         * 开始下载
         */
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(path, fileName);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //下载完成的广播接收者
            if (intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId) {
                context.unregisterReceiver(broadcastReceiver);
                onServiceDownloadCallBack.onSuccess();
            }
        }
    };

    //通过OkHttp下载文件
    public DownloadSupport(OnDownloadListener callBack) {
        this.onDownloadListener = callBack;
    }

    //下载
    public void download(final String url, final String name, final String saveDir) {
        if (!FileSupport.hasFile(saveDir + name)) {//FileUtil.getNameFromUrl(url)
            Request request = new Request.Builder()
                .url(url)
                .build();

            OkHttpClient mOkHttpClient = new OkHttpClient();
            mOkHttpClient.connectTimeoutMillis();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // TODO Auto-generated method stub
                    Message msg = MainHandler.obtainMessage();
                    msg.arg1 = OnFail;
                    MainHandler.sendMessage(msg);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    //储存下载文件的目录
                    String savePath = FileSupport.isExistDir(saveDir);
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File file = new File(savePath, name);
                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            //下载中
                            Message message = Message.obtain();
                            message.arg1 = OnDownloading;
                            message.arg2 = progress;
                            MainHandler.sendMessage(message);
                        }
                        fos.flush();
                        //下载完成
                        Message msg = MainHandler.obtainMessage();
                        msg.arg1 = OnDownloadSuccess;
                        msg.obj = file.getAbsolutePath();
                        MainHandler.sendMessage(msg);
                    } catch (Exception e) {
                        Message msg = MainHandler.obtainMessage();
                        msg.arg1 = OnFail;
                        MainHandler.sendMessage(msg);
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {

                        }
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {

                        }
                    }
                }
            });
        } else {
            Message msg = MainHandler.obtainMessage();
            msg.arg1 = OnDownloadSuccess;
            msg.obj = saveDir + name;
            MainHandler.sendMessage(msg);
        }
    }

    private final Handler MainHandler = new Handler() {

        public void handleMessage(Message message) {
            switch (message.arg1) {
                case OnFail:
                    onDownloadListener.onDownloadFail();
                    break;
                case OnDownloading:
                    onDownloadListener.onDownloading(message.arg2);
                    break;
                case OnDownloadSuccess:
                    onDownloadListener.onDownloadSuccess();
                    break;
                default:
                    break;
            }
        }
    };


    public interface OnServiceDownloadCallBack {
        void onSuccess();
    }

    public interface OnDownloadListener {

        void onDownloadSuccess();

        void onDownloading(int progress);

        void onDownloadFail();
    }

}
