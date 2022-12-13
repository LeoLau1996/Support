package com.leo.support.view.activity;

import android.os.Bundle;
import android.util.Log;

import com.leo.support.R;
import com.leo.support.databinding.ActivityCameraBinding;
import com.leo.support.info.AppPath;

import com.surgery.scalpel.base.activity.CommonActivity;
import com.surgery.scalpel.model.CommonViewModel;
import com.surgery.scalpel.support.file.FileSupport;
import com.surgery.scalpel.util.CommonUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 相机
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/12
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * Camera 显示到 SurfaceView
 * ---------------------------------------------------------------------------------------------
 **/
public class CameraActivity extends CommonActivity<ActivityCameraBinding, CommonViewModel> {

    private String path;

    @Override
    protected int setLayout() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void refreshViews(Object data, int propertyId) {

    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnStart.setOnClickListener(v -> {
            path = String.format("%scamera_%s.h264", AppPath.getAppCache(), System.currentTimeMillis());
            binding.mCameraRecordedView.startRecording();
        });
        binding.btnStop.setOnClickListener(v -> {
            binding.mCameraRecordedView.stopRecord();
            CommonUtil.copy(activity, path);
            Log.e("liu1022", "path = " + path);
        });


        binding.mCameraRecordedView.setCallBack(outputBytes -> {
            // 保存数据到本地
            FileSupport.writeBytes(path, true, outputBytes);
        });
    }
}
