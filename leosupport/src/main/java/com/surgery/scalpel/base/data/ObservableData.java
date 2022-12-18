package com.surgery.scalpel.base.data;


import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述: 一个可以监听属性的数据对象
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/11/25
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注: 设置数据
 * ---------------------------------------------------------------------------------------------
 **/
public class ObservableData<D> extends Observable.OnPropertyChangedCallback implements LifecycleObserver {


    // 数据主体
    private D data;
    // 回调方法
    private OnCommomDataCallBack callBack;

    public ObservableData(Object owner) {
        this(null, owner);
    }

    public ObservableData(D data, Object owner) {
        //
        setData(data);

        if (owner == null) {
            return;
        }
        // 设置数据修改监听
        if (owner instanceof OnCommomDataCallBack) {
            setCallBack((OnCommomDataCallBack) owner);
        }
        // 生命周期监听
        if (owner instanceof LifecycleOwner) {
            ((LifecycleOwner) owner).getLifecycle().addObserver(this);
        } else if (owner instanceof Lifecycle) {
            ((Lifecycle) owner).addObserver(this);
        }
    }

    public D data() {
        return data;
    }

    // 设置数据
    public void setData(D newData) {
        data = newData;
        if (data != null && data instanceof BaseObservable) {
            ((BaseObservable) data).addOnPropertyChangedCallback(this);
        }
        onPropertyChanged(null, BR._all);
    }

    // 设置监听
    public void setCallBack(OnCommomDataCallBack callBack) {
        this.callBack = callBack;
    }

    // 释放
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        Log.e("liu1125", "onDestroy");
        if (data != null && data instanceof BaseObservable) {
            ((BaseObservable) data).removeOnPropertyChangedCallback(this);
        }
        callBack = null;
        data = null;
    }

    // 监听属性
    @Override
    public void onPropertyChanged(Observable sender, int propertyId) {
        if (callBack == null) {
            return;
        }
        callBack.onDataPropertyChanged(data, propertyId);
    }

    public interface OnCommomDataCallBack {

        // 属性值修改
        void onDataPropertyChanged(Object data, int propertyId);

    }

}
