package leo.work.support.base.biz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import leo.work.support.base.LifeControlInterface;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 带有生命周期的Biz
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonLifeBiz {

    // 生命周期控制接口
    private LifeControlInterface lifeControlInterface;

    public CommonLifeBiz(LifeControlInterface lifeControlInterface) {
        this.lifeControlInterface = lifeControlInterface;
        addCommonLifeCallBack();
    }

    //添加
    private void addCommonLifeCallBack() {
        if (lifeControlInterface == null) {
            return;
        }
        lifeControlInterface.addLifeCallBackList(this);
    }

    //删除 (不要在for循环中使用这个删除)
    private void removeLifeCallBackList() {
        if (lifeControlInterface == null) {
            return;
        }
        lifeControlInterface.removeLifeCallBackList(this);
    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    public void dispatchTouchEvent(MotionEvent event) {

    }

    public void onDialogDismiss() {

    }

    //获取Context
    public Context getContext() {
        Context context = null;
        if (lifeControlInterface == null) {
            return context;
        }
        if (lifeControlInterface instanceof Activity) {
            context = (Context) lifeControlInterface;
        } else if (lifeControlInterface instanceof Fragment) {
            context = ((Fragment) lifeControlInterface).getContext();
        }
        return context;
    }

    //获取Activity
    public AppCompatActivity getActivity() {
        AppCompatActivity activity = null;
        if (lifeControlInterface == null) {
            return activity;
        }
        if (lifeControlInterface instanceof AppCompatActivity) {
            activity = (AppCompatActivity) lifeControlInterface;
        } else if (lifeControlInterface instanceof Fragment) {
            activity = (AppCompatActivity) ((Fragment) lifeControlInterface).getActivity();
        }
        return activity;
    }

    public Fragment getFragment() {
        if (!isFragment()) {
            return null;
        }
        return (Fragment) lifeControlInterface;
    }

    public Dialog getDialog() {
        if (!isDialog()) {
            return null;
        }
        return (Dialog) lifeControlInterface;
    }

    public boolean isActivity() {
        if (lifeControlInterface == null) {
            return false;
        }
        return lifeControlInterface instanceof Activity;
    }

    public boolean isFragment() {
        if (lifeControlInterface == null) {
            return false;
        }
        return lifeControlInterface instanceof Fragment;
    }

    public boolean isDialog() {
        if (lifeControlInterface == null) {
            return false;
        }
        return lifeControlInterface instanceof Dialog;
    }

    public LifeControlInterface getLifeControlInterface() {
        return lifeControlInterface;
    }
}
