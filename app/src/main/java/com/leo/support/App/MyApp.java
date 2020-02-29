package com.leo.support.App;

import leo.work.support.Base.Application.ApplicationInfo;
import leo.work.support.Base.Application.BaseApplication;
import leo.work.support.Base.Application.TopBarInfo;
import leo.work.support.Support.Common.Get;

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

    }

    @Override
    protected ApplicationInfo setInfo() {
        return new ApplicationInfo("波波来了", Get.getAppVersionName(), "5cd2a0213fc195fb5e0008ba");
    }

    @Override
    protected TopBarInfo setTopBarInfo() {
        return null;
    }
}
