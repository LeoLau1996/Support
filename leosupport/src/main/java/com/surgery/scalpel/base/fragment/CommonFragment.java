package com.surgery.scalpel.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.surgery.scalpel.BR;
import com.surgery.scalpel.base.data.CommomData;
import com.surgery.scalpel.model.CommonViewModel;
import com.surgery.scalpel.util.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间:  2021/4/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonFragment<T extends ViewDataBinding, M extends ViewModel> extends CommonAbstractFragment implements CommomData.OnCommomDataCallBack {

    public Context context;
    public Activity activity;

    private boolean hasResume = false;//是否前台（不保证正在显示该页面）
    public boolean hasFront = false;//当前页面是否在前台
    public boolean hidden = false;//Fragment显示/隐藏状态

    public T binding;
    public M viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("=======================>" + this.getClass().getName());
        binding = DataBindingUtil.inflate(inflater, setLayout(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        activity = getActivity();
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            viewModel = (M) new ViewModelProvider(this).get((Class) types[1]);
        }
        initData(savedInstanceState);
        refreshViews(savedInstanceState, BR._all);
        loadData();
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
    public void onResume() {
        super.onResume();
        hasResume = true;
        hasFront = !hidden;
    }

    @Override
    public void onPause() {
        super.onPause();
        hasResume = false;
        hasFront = false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (hasResume) {
            this.hasFront = !hidden;
        } else {
            this.hasFront = false;
        }
    }

    @Override
    public void onDataPropertyChanged(Object data, int propertyId) {
        refreshViews(data, propertyId);
    }

}
