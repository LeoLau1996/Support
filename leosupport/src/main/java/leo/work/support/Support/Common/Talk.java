package leo.work.support.Support.Common;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.Base.Util.BaseUtil;

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
public class Talk extends BaseUtil {
    private static Toast mToast;

    public static void talkShort(String detail) {
        Toast.makeText(getContext(), detail, Toast.LENGTH_SHORT).show();
    }

    public static void talkLong(String detail) {
        Toast.makeText(getContext(), detail, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(final CharSequence text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(text)) {
                    TextView toastTextView = (TextView) LayoutInflater.from(getContext()).inflate(BaseApplication.getToastLayoutModel().getLayout(), null);
                    toastTextView.getBackground().mutate().setAlpha(150);
                    toastTextView.setText(text);
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = new Toast(getContext());
                    mToast.setView(toastTextView);
                    mToast.setDuration(Toast.LENGTH_SHORT);


                    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                    DisplayMetrics dm = new DisplayMetrics();
                    wm.getDefaultDisplay().getMetrics(dm);
                    int height = (int) (dm.heightPixels * BaseApplication.getToastLayoutModel().getY());
                    mToast.setGravity(Gravity.BOTTOM, 0, height);
                    mToast.show();
                }
            }
        });
    }

}
