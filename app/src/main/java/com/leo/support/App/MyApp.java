package com.leo.support.App;

import com.leo.support.R;

import leo.work.support.base.application.BaseApplication;
import leo.work.support.support.common.Get;
import leo.work.support.widget.TopBar;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019/5/17
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MyApp extends BaseApplication {

    @Override
    protected void init() {
        TopBar.setDefaultInfo(new TopBar.TopBarDefaultInfo(R.drawable.ic_back_black, R.color.black, R.color.bule, 44));
    }

}
