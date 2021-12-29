package leo.work.support.base.biz;

import android.content.Intent;
import android.os.Bundle;

import leo.work.support.base.activity.CommonActivity;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonLifeBiz implements CommonLifeCallBack {

    private CommonActivity commonActivity;

    public CommonLifeBiz(CommonActivity commonActivity) {
        this.commonActivity = commonActivity;
        addCommonLifeCallBack();
    }

    private void addCommonLifeCallBack() {
        if (commonActivity == null) {
            return;
        }
        commonActivity.addLifeCallBackList(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        commonActivity.removeLifeCallBackList(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }
}
