package com.leo.support.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leo.support.BR;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/11/25
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class BModel extends BaseObservable {


    private String name;
    private int age2;
    private double data2;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getAge2() {
        return age2;
    }

    public void setAge2(int age2) {
        this.age2 = age2;
        notifyPropertyChanged(BR.age2);
    }

    @Bindable
    public double getData2() {
        return data2;
    }

    public void setData2(double data2) {
        this.data2 = data2;
        notifyPropertyChanged(BR.data2);
    }
}
