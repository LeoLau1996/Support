package com.surgery.scalpel.base.activity;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.surgery.scalpel.BR;
import com.surgery.scalpel.base.data.ObservableData;
import com.surgery.scalpel.model.CommonViewModel;
import com.surgery.scalpel.util.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 简单的Activity
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2021/4/21
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonActivity<T extends ViewDataBinding, M extends CommonViewModel> extends CommonAbstractActivity implements ObservableData.OnCommomDataCallBack {

    // Activity
    public CommonActivity activity;
    // ViewDataBinding
    public T binding;
    // ViewModel
    public M viewModel;
    // 当前页面是否在前台
    public boolean hasFront = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印Log
        LogUtil.e("=======================>" + this.getClass().getName());
        //
        activity = this;
        //
        binding = DataBindingUtil.setContentView(activity, setLayout());

        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            viewModel = (M) new ViewModelProvider(this).get((Class) types[1]);
            viewModel.setOwner(this);
        }

        //初始化数据
        initData(savedInstanceState);
        //加载View
        refreshViews(null, BR._all);
        //加载数据
        loadData();
        //初始化监听器
        initListener();
    }


    protected abstract int setLayout();

    // 初始化数据
    protected abstract void initData(Bundle savedInstanceState);

    // 加载View
    protected abstract void refreshViews(Object data, int propertyId);

    // 加载数据，如：网络请求
    protected void loadData() {

    }

    // 初始化监听器
    protected void initListener() {

    }

    /**
     * 使用时应该写在这上面
     * ....
     * super.onResume();
     * 不应该写在super下面
     */
    @Override
    protected void onResume() {
        super.onResume();
        hasFront = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        hasFront = false;
    }

    @Override
    public void onDataPropertyChanged(Object data, int propertyId) {
        refreshViews(data, propertyId);
        // 驱动Biz的refreshViews
        for (int i = 0; bizList != null && i < bizList.size(); i++) {
            bizList.get(i).refreshViews(data, propertyId);
        }
    }

}
