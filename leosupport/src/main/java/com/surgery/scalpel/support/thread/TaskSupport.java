package com.surgery.scalpel.support.thread;

import android.os.AsyncTask;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:AsyncTask封装
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/10
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TaskSupport extends AsyncTask<LeoRunnable, Void, LeoRunnable> {
    private LeoRunnable.LeoTaskListener listener;
    private Object result;

    //异步任务
    @Override
    protected LeoRunnable doInBackground(LeoRunnable... leoRunnables) {
        LeoRunnable item = leoRunnables[0];
        listener = item.getListener();
        if (listener != null) {
            result = listener.getObject();
        }
        return item;
    }

    //返回结果
    @Override
    protected void onPostExecute(LeoRunnable leoRunnable) {
        if (listener == null || result == null) {
            return;
        }
        listener.update(result);
    }
}
