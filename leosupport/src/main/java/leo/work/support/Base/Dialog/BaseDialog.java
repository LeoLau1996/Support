package leo.work.support.Base.Dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import leo.work.support.R;
import leo.work.support.Support.Common.LogUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/5/28
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class BaseDialog extends DialogFragment {
    //默认样式
    public Context context;
    public Activity activity;
    public View view;
    public static String DIALOG_STATUS_SHOW = "显示", DIALOG_STATUS_DISMISS = "隐藏";
    public String dialogStatus = DIALOG_STATUS_DISMISS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("=======================>" + this.getClass().getName());
        int style = setStyle_();
        if (style == 0) {
            style = R.style.Theme_Light_DialogFalse;
        }
        setStyle(DialogFragment.STYLE_NORMAL, style);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(setLayout(), container, false);
        activity = getActivity();
        context = activity.getApplicationContext();
        dialogStatus = DIALOG_STATUS_SHOW;

        initData();

        initViews();

        loadData();

        initListener();
        return view;
    }

    /**
     * 重写onStart方法，解决弹窗宽度不占满问题
     */
    public void onStart() {
        super.onStart();
        if (hasZM()) {
            Window win = getDialog().getWindow();
            // 一定要设置Background，如果不设置，window属性设置无效
            win.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transp)));
            //启动和关闭动画
            win.setWindowAnimations(R.style.animate_dialog);

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            WindowManager.LayoutParams params = win.getAttributes();
            params.gravity = Gravity.BOTTOM;
            // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;

            win.setAttributes(params);
        }
    }

    //设置样式
    protected abstract int setStyle_();

    //设置布局
    protected abstract int setLayout();

    //是否宽度占满
    protected abstract boolean hasZM();

    //初始化数据
    protected abstract void initData();

    //初始化视图
    protected abstract void initViews();

    //网络加载
    protected abstract void loadData();

    //设置监听
    protected abstract void initListener();

    /**
     * 显示Dialog
     *
     * @param activity
     * @return
     */
    public boolean showDialog(Activity activity) {
        return showDialog(activity, "TAG");
    }

    public boolean showDialog(Activity activity, String TAG) {
        if (dialogStatus.equals(DIALOG_STATUS_DISMISS)) {
            show(activity.getFragmentManager(), TAG);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {

        }
    }

    public void dismissDialog() {
        try {
            if (getActivity() != null && !getActivity().isFinishing()) {
                super.dismissAllowingStateLoss();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dialogStatus = DIALOG_STATUS_DISMISS;
    }


    public String getDialogStatus() {
        return dialogStatus;
    }

}