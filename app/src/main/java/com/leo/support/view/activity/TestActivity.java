package com.leo.support.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.databinding.BaseObservable;

import com.google.gson.Gson;
import com.leo.support.R;
import com.leo.support.databinding.ActivityTestBinding;
import com.leo.support.model.TestModel;

import com.surgery.scalpel.base.activity.CommonActivity;
import com.surgery.scalpel.base.data.CommomData;
import com.surgery.scalpel.model.CommonViewModel;
import com.surgery.scalpel.util.A2BSupport;
import com.surgery.scalpel.util.JumpUtil;


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
public class TestActivity extends CommonActivity<ActivityTestBinding, CommonViewModel> {

    public static void go(Activity activity) {
        JumpUtil.go(activity, TestActivity.class);
    }

    private static class TestActivityModel extends BaseObservable {


    }

    /*********************
     *     全 局 变 量    *
     *********************/

    private CommomData<TestActivityModel> activityModel;
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
        testModel = new CommomData<>(new TestModel(), this);
    }

    @Override
    protected void refreshViews(Object data, int propertyId) {
        String text = new Gson().toJson(data);
        Log.e("liu1125", String.format("onPropertyChanged    data = %s    propertyId = %s", text, propertyId));
        binding.tvContent.setText(text);
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString();
                testModel.data().setName(name);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ageStr = s.toString();
                int age = A2BSupport.toInt(ageStr);
                testModel.data().setAge(age);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 设置姓名
        binding.btnName.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            testModel.data().setName(name);
        });
        // 设置年龄
        binding.btnAge.setOnClickListener(v -> {
            String ageStr = binding.etAge.getText().toString();
            int age = A2BSupport.toInt(ageStr);
            testModel.data().setAge(age);
        });
    }

    /*********************
     *     业 务 方 法    *
     *********************/


}