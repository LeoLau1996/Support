package com.leo.support.biz;

import android.os.Bundle;
import android.view.View;

import com.leo.support.view.activity.ActivityMainTestViewGroup;
import com.surgery.scalpel.base.LifeControlInterface;
import com.surgery.scalpel.base.biz.CommonLifeBiz;

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
public class TestBiz extends CommonLifeBiz {

    private ActivityMainTestViewGroup testViewGroup;

    public TestBiz(LifeControlInterface lifeControlInterface, View rootView) {
        super(lifeControlInterface);
        testViewGroup = new ActivityMainTestViewGroup(this, rootView);
    }

    public void xxx() {
        testViewGroup.btnMenu.setText(String.valueOf(System.currentTimeMillis()));
    }
}
