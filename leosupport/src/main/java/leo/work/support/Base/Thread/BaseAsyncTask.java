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
