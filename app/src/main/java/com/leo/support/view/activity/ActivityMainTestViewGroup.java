package com.leo.support.view.activity;


import android.view.View;
import android.widget.Button;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.leo.support.R;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/13
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class ActivityMainTestViewGroup implements LifecycleObserver {

    public Button btnMenu;

    public ActivityMainTestViewGroup(Object owner, View rootView) {
        if (owner instanceof LifecycleOwner)
            ((LifecycleOwner) owner).getLifecycle().addObserver(this);

        btnMenu = rootView.findViewById(R.id.btnMenu);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroy() {
        btnMenu = null;
    }

}
