package leo.work.support.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2018/4/19.
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonAbstractFragment extends Fragment implements LifeControlInterface {

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
        Log.e("liu0708", "Fragment    准备删除生命周期对象    lifeBizList = " + bizList.size());
        bizList.remove(biz);
        Log.e("liu0708", "Fragment    删除完成生命周期对象    lifeBizList = " + bizList.size());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).onSaveInstanceState(outState);
        }
    }


}
