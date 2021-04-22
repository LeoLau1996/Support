package leo.work.support.support.thread;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/10
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class LeoRunnable {
    private int position;
    private LeoTaskListener listener;


    public LeoRunnable(LeoTaskListener listener) {
        this.listener = listener;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public LeoTaskListener getListener() {
        return listener;
    }

    public void setListener(LeoTaskListener listener) {
        this.listener = listener;
    }


    public static abstract class LeoTaskListener {
        public LeoTaskListener() {

        }

        //异步任务
        public abstract <T> T getObject();

        //返回结果
        public abstract <T> void update(T var1);
    }
}
