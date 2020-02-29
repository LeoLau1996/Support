package leo.work.support.Support.Thread;

import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;

/**
 * Created by 刘桂安 on 2018/6/8.
 */


public class LeoTimerTask extends TimerTask {

    LeoTimerTaskListener listener = null;

    public LeoTimerTask(LeoTimerTaskListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        Message message = new Message();
        handler.sendMessage(message);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (listener != null) {
                listener.doIt();
                listener.addCount();
            }
            super.handleMessage(msg);
        }

        ;
    };


    public static abstract class LeoTimerTaskListener {
        int count = 0;

        protected abstract void doIt();

        public int getCount() {
            return count;
        }

        public void addCount() {
            count++;
        }
    }
}