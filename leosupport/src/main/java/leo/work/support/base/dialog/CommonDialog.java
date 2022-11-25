package leo.work.support.base.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import leo.work.support.BR;
import leo.work.support.R;
import leo.work.support.base.data.CommomData;
import leo.work.support.util.LogUtil;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/8/5
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public abstract class CommonDialog<T extends ViewDataBinding> extends CommonAbstractDialog implements LifecycleObserver, CommomData.OnCommomDataCallBack {

    public T binding;
    public Context context;

    public CommonDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CommonDialog(@NonNull Context context, int style) {
        super(context, style);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打印Log
        LogUtil.e("=======================>" + this.getClass().getName());
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), setLayout(), null, false);
        setContentView(binding.getRoot());

        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }

        //初始化数据
        initData();
        //加载View
        initViews(null, BR._all);
        //加载数据
        loadData();
        //初始化监听器
        initListener();
    }

    // 设置布局
    protected abstract int setLayout();

    // 初始化数据
    protected abstract void initData();

    // 加载View
    protected abstract void initViews(Object data, int propertyId);

    // 加载数据，如：网络请求
    protected void loadData() {

    }

    // 初始化监听器
    protected void initListener() {

    }

    @Override
    public void onDataPropertyChanged(Object data, int propertyId) {
        initViews(data, propertyId);
    }

}
