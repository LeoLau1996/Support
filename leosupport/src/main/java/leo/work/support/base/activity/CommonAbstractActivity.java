package leo.work.support.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import leo.work.support.base.LifeControlInterface;
import leo.work.support.base.biz.CommonLifeBiz;
import leo.work.support.util.CommonUtil;


public abstract class CommonAbstractActivity extends AppCompatActivity implements LifeControlInterface {

    private List<CommonLifeBiz> lifeBizList;

    @Override
    public void addLifeCallBackList(CommonLifeBiz biz) {
        if (lifeBizList == null) {
            lifeBizList = new ArrayList<>();
        }
        if (biz == null) {
            return;
        }
        lifeBizList.add(biz);
    }

    @Override
    public void removeLifeCallBackList(CommonLifeBiz biz) {
        if (lifeBizList == null) {
            return;
        }
        if (biz == null) {
            return;
        }
        Log.e("liu0708", "Activity    准备删除生命周期对象    lifeBizList = " + lifeBizList.size());
        lifeBizList.remove(biz);
        Log.e("liu0708", "Activity    删除完成生命周期对象    lifeBizList = " + lifeBizList.size());
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onDestroy();
        }
        if (lifeBizList != null) {
            lifeBizList.clear();
        }
        lifeBizList = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        for (int i = 0; lifeBizList != null && i < lifeBizList.size(); i++) {
            lifeBizList.get(i).dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

}
