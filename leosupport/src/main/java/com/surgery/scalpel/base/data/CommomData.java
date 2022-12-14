package com.surgery.scalpel.base.data;


import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.surgery.scalpel.base.activity.CommonActivity;
import com.surgery.scalpel.base.dialog.CommonDialog;
import com.surgery.scalpel.base.dialog.CommonDialogFragment;
import com.surgery.scalpel.base.fragment.CommonFragment;

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
public class CommomData<D extends BaseObservable> extends Observable.OnPropertyChangedCallback implements LifecycleObserver {


    // 数据主体
    private D data;
    // 回调方法
    private OnCommomDataCallBack callBack;

    public CommomData() {
        this(null, null);
    }

    public CommomData(Object owner) {
        this(null, owner);
    }

    public CommomData(D data) {
        this(data, null);
    }

    public CommomData(D data, Object owner) {
        setData(data);
        if (owner == null) {
            return;
        }
        if (owner instanceof OnCommomDataCallBack) {
            setCallBack((OnCommomDataCallBack) owner);
        }
        if (owner instanceof CommonActivity) {
            ((LifecycleOwner) owner).getLifecycle().addObserver(this);
        } else if (owner instanceof CommonFragment) {
            ((CommonFragment) owner).getLifecycle().addObserver(this);
        } else if (owner instanceof CommonDialog) {
            ((LifecycleOwner) ((CommonDialog) owner).getContext()).getLifecycle().addObserver(this);
        } else if (owner instanceof CommonDialogFragment) {
            ((CommonDialogFragment) owner).getLifecycle().addObserver(this);
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
        if (data != null) {
            data.addOnPropertyChangedCallback(this);
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
        if (data != null) {
            data.removeOnPropertyChangedCallback(this);
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
