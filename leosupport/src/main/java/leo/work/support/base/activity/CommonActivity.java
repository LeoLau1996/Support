package leo.work.support.base.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import leo.work.support.base.biz.CommonLifeCallBack;


public abstract class CommonActivity extends AppCompatActivity {

    private List<CommonLifeCallBack> lifeCallBackList;

    public void addLifeCallBackList(CommonLifeCallBack callBack) {
        if (lifeCallBackList == null) {
            lifeCallBackList = new ArrayList<>();
        }
        if (callBack == null) {
            return;
        }
        lifeCallBackList.add(callBack);
    }

    public void removeLifeCallBackList(CommonLifeCallBack callBack) {
        if (lifeCallBackList == null) {
            return;
        }
        if (callBack == null) {
            return;
        }
        lifeCallBackList.remove(callBack);
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (int i = 0; lifeCallBackList != null && i < lifeCallBackList.size(); i++) {
            lifeCallBackList.get(i).onRestoreInstanceState(savedInstanceState);
        }
    }

}
