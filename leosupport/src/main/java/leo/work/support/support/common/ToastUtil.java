package leo.work.support.support.common;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import leo.work.support.base.application.BaseApplication;
import leo.work.support.util.BaseUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/16
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class ToastUtil extends BaseUtil {
    private static Toast mToast;

    public static void showToast(final CharSequence text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(text)) {
                    return;
                }
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(BaseApplication.getToastLayoutModel().getLayout(), null);
                textView.getBackground().mutate().setAlpha(150);
                textView.setText(text);
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = new Toast(getContext());
                mToast.setView(textView);
                mToast.setDuration(Toast.LENGTH_SHORT);

                WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                int height = (int) (displayMetrics.heightPixels * BaseApplication.getToastLayoutModel().getY());
                mToast.setGravity(Gravity.BOTTOM, 0, height);
                mToast.show();
            }
        });
    }

}
