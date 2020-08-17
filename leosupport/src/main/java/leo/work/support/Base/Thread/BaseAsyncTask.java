package leo.work.support.Base.Thread;

import android.os.AsyncTask;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2020/8/13
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * 缺点
 * 当线程池已满128时   再新增现场会报RejectedExecutionException
 * 过多的线程会引起大量消耗系统资源和导致应用FC的风险。
 * AsyncTask的生命周期与Activity生命周期不同步
 * 如果在doInBackgroud里有一个不可中断的操作，比如BitmapFactory.decodeStream()，调用了cancle() 也未必能真正地取消任务。
 * 如果AsyncTask被声明为Activity的非静态的内部类，那么AsyncTask会保留一个对创建了AsyncTask的Activity的引用。如果Activity已经被销毁，AsyncTask的后台线程还在执行，它将继续在内存里保留这个引用，导致Activity无法被回收，引起内存泄露。
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class BaseAsyncTask extends AsyncTask {

    /**
     * 在执行前
     * 主线程
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        init();
    }


    /**
     * 耗时操作
     * 子线程
     */
    @Override
    protected Object doInBackground(Object[] objects) {
        return doIng(objects);
    }

    /**
     * 进度更新    调用publishProgress方法
     * 主线程
     */
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        onProgress(values);
    }

    /**
     * 完毕
     * 主线程
     */
    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        success(result);
    }

    abstract void init();

    abstract Object doIng(Object[] objects);

    abstract void onProgress(Object[] values);

    abstract void success(Object result);
}
