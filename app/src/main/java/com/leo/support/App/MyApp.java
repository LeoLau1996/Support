package com.leo.support.App;

import com.leo.support.R;
import leo.work.support.Base.Application.ApplicationInfo;
import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.Support.Common.Get;
import leo.work.support.Widget.TopBar;

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
        TopBar.setDefaultInfo(new TopBar.TopBarDefaultInfo(R.drawable.ic_back_black, R.color.black, R.color.bule));
    }

    @Override
    protected ApplicationInfo setInfo() {
        return new ApplicationInfo("波波来了", Get.getAppVersionName(), "5cd2a0213fc195fb5e0008ba");
    }

}
