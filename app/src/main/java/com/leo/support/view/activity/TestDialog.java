package com.leo.support.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.leo.support.R;
import com.leo.support.databinding.DialogTestBinding;

import leo.work.support.base.dialog.CommonDialog;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/31
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TestDialog extends CommonDialog<DialogTestBinding>  {

    private static TestDialog instance;

    public static synchronized void showTestDialog(Context context, OnTestDialogCallBack callBack) {
        dismissDialog();
        instance = new TestDialog(context, callBack);
        instance.show();
    }

    public static synchronized void dismissDialog() {
        try {
            if ((instance != null) && instance.isShowing()) {
                instance.dismiss();
            }
        } catch (final Exception e) {

        } finally {
            instance = null;
        }
    }

    /*********************
     *     全 局 变 量    *
     *********************/
    private OnTestDialogCallBack callBack;

    /*********************
     *  生 命 周 期 方 法  *
     *********************/

    public TestDialog(@NonNull Context context, OnTestDialogCallBack callBack) {
        super(context);
        this.callBack = callBack;
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {



    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 必须设置这两个,才能设置宽度
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        // 遮罩层透明度
        //window.setDimAmount(0);
        // 设置宽度
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        //params.windowAnimations = R.style.dialog_bottom_animation;
        window.setAttributes(params);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroy() {
        dismissDialog();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    /*********************
     *     业 务 方 法    *
     *********************/

    public interface OnTestDialogCallBack {

    }

}