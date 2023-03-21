package com.surgery.scalpel.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.surgery.scalpel.base.LifeControlInterface;
import com.surgery.scalpel.base.biz.CommonLifeBiz;
import com.surgery.scalpel.util.Is;


public abstract class CommonAbstractActivity extends AppCompatActivity implements LifeControlInterface {

    protected List<CommonLifeBiz> bizList;

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
        Log.e("liu0708", "Activity    准备删除生命周期对象    lifeBizList = " + bizList.size());
        bizList.remove(biz);
        Log.e("liu0708", "Activity    删除完成生命周期对象    lifeBizList = " + bizList.size());
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
    protected void onResume() {
        super.onResume();
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onPause();
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
    protected void onDestroy() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

}
