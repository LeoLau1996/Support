package com.leo.support.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.leo.support.R;
import com.leo.support.databinding.ActivityTestBinding;
import com.leo.support.model.TestModel;

import leo.work.support.base.activity.CommonActivity;
import leo.work.support.base.data.CommomData;
import leo.work.support.util.A2BSupport;
import leo.work.support.util.JumpUtil;


/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/10/31
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TestActivity extends CommonActivity<ActivityTestBinding> implements CommomData.OnCommomDataCallBack<TestModel> {

    public static void go(Activity activity) {
        JumpUtil.go(activity, TestActivity.class);
    }

    /*********************
     *     全 局 变 量    *
     *********************/

    private CommomData<TestModel> testModel;

    /*********************
     *  生 命 周 期 方 法  *
     *********************/

    @Override
    protected int setLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData(Bundle bundle) {
        testModel = new CommomData<>(getLifecycle(), new TestModel(), this);
    }

    @Override
    protected void initViews(Bundle bundle) {

    }

    @Override
    protected void initListener() {
        super.initListener();
        // 设置姓名
        binding.btnName.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            testModel.data.setName(name);
        });
        // 设置年龄
        binding.btnAge.setOnClickListener(v -> {
            String ageStr = binding.etAge.getText().toString();
            int age = A2BSupport.String2int(ageStr);
            testModel.data.setAge(age);
        });
    }

    /*********************
     *     业 务 方 法    *
     *********************/

    @Override
    public void onDataPropertyChanged(TestModel data, int propertyId) {
        Log.e("liu1125", String.format("onPropertyChanged    data = %s    propertyId = %s", new Gson().toJson(data), propertyId));
    }

}