package leo.work.support.base.data;


import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
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
public class CommomData<D extends BaseObservable> extends Observable.OnPropertyChangedCallback implements LifecycleObserver {

    // 数据主体
    public D data;
    // 回调方法
    private OnCommomDataCallBack callBack;

    public CommomData(Lifecycle lifecycle) {
        this(lifecycle, null, null);
    }

    public CommomData(Lifecycle lifecycle, D data) {
        this(lifecycle, data, null);
    }

    public CommomData(Lifecycle lifecycle, OnCommomDataCallBack callBack) {
        this(lifecycle, null, callBack);
    }

    public CommomData(Lifecycle lifecycle, D data, OnCommomDataCallBack callBack) {
        setData(data);
        setCallBack(callBack);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
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
