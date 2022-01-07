package com.leo.support.view.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.leo.support.R;

import leo.work.support.support.toolSupport.LeoSupport;
import leo.work.support.util.CommonUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeoSupport.fullScreen(this, false);
        setContentView(R.layout.activity_main);
    }

}
