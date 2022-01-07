package com.leo.support.app;

import com.leo.support.R;

import leo.work.support.base.application.BaseApplication;
import leo.work.support.util.A2BSupport;
import leo.work.support.widget.bar.TitleBarDefaultInfo;

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
        TitleBarDefaultInfo.setTitleBarDefaultInfo(new TitleBarDefaultInfo(A2BSupport.dp2px(14),R.drawable.ic_back_black, R.color.black, R.color.bule, A2BSupport.dp2px(50),R.color.line));
    }

}
