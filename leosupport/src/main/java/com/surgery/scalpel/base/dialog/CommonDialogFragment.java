package com.surgery.scalpel.base.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.surgery.scalpel.BR;
import com.surgery.scalpel.R;
import com.surgery.scalpel.base.data.ObservableData;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/8/10
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonDialogFragment<T extends ViewDataBinding, M extends ViewModel> extends CommonAbstractDialogFragment implements LifecycleObserver, ObservableData.OnCommomDataCallBack {

    public T binding;
    public M viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CommonDialogFragmentFullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, setLayout(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLifecycle().addObserver(this);
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            viewModel = (M) new ViewModelProvider(this).get((Class) types[1]);
        }
        initData(savedInstanceState);
        refreshViews(null, BR._all);
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

    @Override
    public void onDataPropertyChanged(Object data, int propertyId) {
        refreshViews(data, propertyId);
    }

    // 设置背景颜色
    public void setDialogBackgroundColor(int color) {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(color));
        window.setBackgroundDrawable(colorDrawable);
    }

}