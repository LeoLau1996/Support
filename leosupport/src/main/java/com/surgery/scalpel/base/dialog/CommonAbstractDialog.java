package com.surgery.scalpel.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.surgery.scalpel.base.LifeControlInterface;
import com.surgery.scalpel.base.biz.CommonLifeBiz;
import com.surgery.scalpel.util.Is;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/8/5
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonAbstractDialog extends Dialog implements LifeControlInterface{

    protected List<CommonLifeBiz> bizList;

    public CommonAbstractDialog(@NonNull Context context) {
        super(context);
    }

    public CommonAbstractDialog(@NonNull Context context, int style) {
        super(context, style);
    }

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
    public <B extends CommonLifeBiz> B obtainBiz(Class<B> bizClass) {
        return obtainBiz("", bizClass);
    }

    @Override
    public <B extends CommonLifeBiz> B obtainBiz(String bizTag, Class<B> bizClass) {
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            CommonLifeBiz biz = bizList.get(i);
            if (biz.getClass() == bizClass) {
                continue;
            }
            if (!Is.isEquals(biz.getBizTag(), bizTag)) {
                continue;
            }
            return (B) biz;
        }
        try {
            Constructor constructor = bizClass.getConstructor(LifeControlInterface.class, Bundle.class, String.class);
            B biz = (B) constructor.newInstance(this, null, bizTag);
            addLifeCallBackList(biz);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onStop();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (bizList != null) {
            Iterator<CommonLifeBiz> iterator = bizList.iterator();
            while (iterator.hasNext()) {
                CommonLifeBiz biz = iterator.next();
                biz.onDialogDismiss();
                Log.e("liu0708", "Activity    准备删除生命周期对象    lifeBizList = " + bizList.size());
                iterator.remove();
                Log.e("liu0708", "Activity    删除完成生命周期对象    lifeBizList = " + bizList.size());
            }
        }
        bizList = null;
    }

}
