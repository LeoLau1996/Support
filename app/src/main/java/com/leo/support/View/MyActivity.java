package com.leo.support.View;

import android.os.Bundle;
import android.util.Log;

import com.leo.support.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import leo.work.support.Base.Activity.BaseActivity;
import leo.work.support.widget.TopBar;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2020/11/30
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MyActivity extends BaseActivity {
    @BindView(R.id.mTopBar)
    TopBar mTopBar;

    @Override
    protected int setLayout() {
        return R.layout.activity_my;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mTopBar.setCallBack(new TopBar.OnTopBarCallBack() {
            @Override
            public void onClickBack() {
                Log.e("liu1130", "onClickBack");
            }

            @Override
            public void onClickMenu() {
                Log.e("liu1130", "onClickMenu");
            }
        });
    }

}
