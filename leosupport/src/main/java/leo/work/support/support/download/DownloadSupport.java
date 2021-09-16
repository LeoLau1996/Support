package leo.work.support.support.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import leo.work.support.support.file.FileSupport;
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
    private OnDownloadListener callBack;
    final int OnFail = 0, OnDownloading = 1, OnDownloadSuccess = 2;


    //下载
    public void download(final String url, final String name, final String saveDir, OnDownloadListener callBack) {
        if (FileSupport.hasFile(saveDir + name)) {
            callBack.onDownloadSuccess();
            return;
        }
        this.callBack = callBack;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.connectTimeoutMillis();
        mOkHttpClient.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
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
                        Message message = MainHandler.obtainMessage();
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
    }

    private final Handler MainHandler = new Handler() {

        public void handleMessage(Message message) {
            switch (message.arg1) {
                case OnFail:
                    callBack.onDownloadFail();
                    break;
                case OnDownloading:
                    callBack.onDownloading(message.arg2);
                    break;
                case OnDownloadSuccess:
                    callBack.onDownloadSuccess();
                    break;
                default:
                    break;
            }
        }
    };

    public interface OnDownloadListener {

        void onDownloadSuccess();

        void onDownloading(int progress);

        void onDownloadFail();
    }

}
