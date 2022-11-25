package com.leo.support.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.leo.support.BR;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/11/24
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TestModel extends BaseObservable {

    private String name;
    private int age;
    private double data;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }


}
