package com.leo.support.View.View;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.leo.support.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import leo.work.support.MVP.BaseView;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-08-05
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MainView extends BaseView {
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this, mRootView);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        tv1.setOnClickListener((View.OnClickListener) baseListener);
        tv2.setOnClickListener((View.OnClickListener) baseListener);
        tv3.setOnClickListener((View.OnClickListener) baseListener);
        tv4.setOnClickListener((View.OnClickListener) baseListener);
    }

    public void get() {


    }
}
