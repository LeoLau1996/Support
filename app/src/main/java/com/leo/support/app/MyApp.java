package com.leo.support.app;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.auto.apt_processor.LayoutViewsGroupConfig;
import com.auto.apt_processor.Utils;
import com.leo.support.BuildConfig;
import com.leo.support.R;

import com.leo.support.model.AccessibillityEvent;
import com.surgery.scalpel.base.application.BaseApplication;
import com.surgery.scalpel.util.A2BSupport;
import com.surgery.scalpel.widget.bar.TitleBarDefaultInfo;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
@LayoutViewsGroupConfig(packageName = BuildConfig.APPLICATION_ID, layoutPaths = {
        "/Users/leolau/Documents/LeoWork/Support/app/src/main/res/layout"
})
public class MyApp extends BaseApplication {

    private Map<String, String> map;

    private final Random random = new Random();
    private final Handler handler = new Handler(msg -> {
        EventBus.getDefault().post(new AccessibillityEvent());
        xxx();
        return false;
    });

    @Override
    public void onCreate() {
        super.onCreate();
        TitleBarDefaultInfo.setTitleBarDefaultInfo(new TitleBarDefaultInfo(A2BSupport.dp2px(14), R.mipmap.ic_back, R.color.white, R.color.color_01c4b6, A2BSupport.dp2px(44), R.color.color_01c4b6));
        xxx();
    }

    private void xxx() {
        handler.sendEmptyMessageDelayed(0, 2000 + random.nextInt(8000));
    }

}
