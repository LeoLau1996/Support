package com.leo.support.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.leo.support.BR;
import com.leo.support.R;
import com.leo.support.biz.LoginBiz;
import com.leo.support.biz.TestBiz;
import com.leo.support.databinding.ActivityAccessibilityBinding;
import com.leo.support.model.AccessibillityEvent;
import com.leo.support.service.NewAccessibilityService;
import com.leo.support.utils.OpenAccessibilitySettingHelper;
import com.surgery.scalpel.base.activity.CommonActivity;
import com.surgery.scalpel.base.biz.CommonLifeBiz;
import com.surgery.scalpel.model.CommonViewModel;
import com.surgery.scalpel.util.JumpUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/14
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class AccessibilityActivity extends CommonActivity<ActivityAccessibilityBinding, CommonViewModel> {

    public static void open(Activity activity) {
        JumpUtil.go(activity, AccessibilityActivity.class);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_accessibility;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void refreshViews(Object data, int propertyId) {
        boolean isAccessibility = OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(activity, NewAccessibilityService.class.getName());
        binding.tvPermissionSwitchStatus.setText(String.format("权限开关状态：%s", isAccessibility));
        binding.btnApply.setVisibility(!isAccessibility ? View.VISIBLE : View.GONE);

        obtainBiz(TestBiz.class).xxx();
        obtainBiz(LoginBiz.class);
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnApply.setOnClickListener(v -> {
            boolean isAccessibility = OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(activity, NewAccessibilityService.class.getName());
            if (isAccessibility) {
                return;
            }
            // 跳转到开启页面
            OpenAccessibilitySettingHelper.jumpToSettingPage(activity);
        });
        binding.btnTest.setOnClickListener(v -> {
            binding.btnTest.setText(String.valueOf(System.currentTimeMillis()));
        });
        //binding.btnTest.setOnTouchListener(new View.OnTouchListener() {
        //    @Override
        //    public boolean onTouch(View v, MotionEvent event) {
        //        Log.e("liu", event.getAction() + "?");
        //        return false;
        //    }
        //});
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshViews(null, BR._all);
    }
}
