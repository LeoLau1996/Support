package leo.work.support.base.biz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

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

    public LifeControlInterface lifeControlInterface;

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

    //删除
    private void removeLifeCallBackList() {
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
        removeLifeCallBackList();
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
}
