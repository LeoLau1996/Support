package leo.work.support.Base.Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2021/2/22
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class SingleThreadBiz {


    private static SingleThreadBiz instance;

    //获取实例
    public static synchronized SingleThreadBiz getInstance() {
        if (instance == null) {
            instance = new SingleThreadBiz();
        }
        return instance;
    }

    //创建Single线程池
    private ExecutorService singleThreadExecutor;

    public void execute(Runnable runnable) {
        if (singleThreadExecutor == null) {
            singleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        singleThreadExecutor.execute(runnable);
    }

    public ExecutorService getSingleThreadExecutor() {
        return singleThreadExecutor;
    }

}
