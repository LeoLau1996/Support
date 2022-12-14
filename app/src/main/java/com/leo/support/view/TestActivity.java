package com.leo.support.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.leo.support.R;
import com.leo.support.databinding.ActivityTestBinding;
import com.leo.support.model.UserModel;

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
public class TestActivity extends AppCompatActivity  {

    private ActivityTestBinding testBinding;
    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        userModel = new UserModel();
        userModel.setName(String.valueOf(System.currentTimeMillis()));
        testBinding.setUserModel(userModel);
        testBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userModel.setName(String.valueOf(System.currentTimeMillis()));
            }
        });
        testBinding.btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBinding.setUserModel(userModel);
            }
        });
    }


}
