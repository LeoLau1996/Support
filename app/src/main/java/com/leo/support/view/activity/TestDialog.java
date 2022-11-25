package com.leo.support.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.leo.support.R;
import com.leo.support.databinding.DialogTestBinding;


import leo.work.support.base.dialog.CommonDialogFragment;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/11/1
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TestDialog extends CommonDialogFragment<DialogTestBinding> {

    private static TestDialog instance;

    public static synchronized void showTestDialog(FragmentManager fragmentManager, OnTestDialogCallBack callBack) {
        dismissDialog();
        instance = new TestDialog();
        instance.setCallBack(callBack);
        String TAG = TestDialog.class.getSimpleName();
        instance.show(fragmentManager, TAG);
    }

    public static void dismissDialog() {
        try {
            if (instance == null || !instance.isVisible()) {
                return;
            }
            instance.dismissAllowingStateLoss();
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

    // 不要在构造方法中加入参数
    public TestDialog() {

    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Object data, int propertyId) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroy() {
        Log.e("liu1101", "destroy");
        dismissDialog();
    }

    /*********************
     *     业 务 方 法    *
     *********************/

    // 设置回调
    public void setCallBack(OnTestDialogCallBack callBack) {
        this.callBack = callBack;
    }


    public interface OnTestDialogCallBack {

    }

}