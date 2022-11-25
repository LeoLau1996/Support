package com.surgery.scalpel.base.dialog;

import android.content.DialogInterface;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.surgery.scalpel.base.LifeControlInterface;
import com.surgery.scalpel.base.biz.CommonLifeBiz;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/8/10
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonAbstractDialogFragment extends DialogFragment implements LifeControlInterface {

    private List<CommonLifeBiz> bizList;

    @Override
    public void addLifeCallBackList(CommonLifeBiz biz) {
        if (bizList == null) {
            bizList = new ArrayList<>();
        }
        if (biz == null) {
            return;
        }
        bizList.add(biz);
    }

    @Override
    public void removeLifeCallBackList(CommonLifeBiz biz) {
        if (bizList == null) {
            return;
        }
        if (biz == null) {
            return;
        }
        Log.e("liu0708", "Dialog    准备删除生命周期对象    lifeBizList = " + bizList.size());
        bizList.remove(biz);
        Log.e("liu0708", "Dialog    删除完成生命周期对象    lifeBizList = " + bizList.size());
    }

    @Override
    public void onStart() {
        super.onStart();
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onResume();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onDialogDismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bizList != null) {
            Iterator<CommonLifeBiz> iterator = bizList.iterator();
            while (iterator.hasNext()) {
                CommonLifeBiz biz = iterator.next();
                biz.onDestroy();
                Log.e("liu0708", "Activity    准备删除生命周期对象    lifeBizList = " + bizList.size());
                iterator.remove();
                Log.e("liu0708", "Activity    删除完成生命周期对象    lifeBizList = " + bizList.size());
            }
        }
        bizList = null;
    }
}
