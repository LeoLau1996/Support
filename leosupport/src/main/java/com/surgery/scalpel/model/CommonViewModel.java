package com.surgery.scalpel.model;

import androidx.lifecycle.ViewModel;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/6
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class CommonViewModel extends ViewModel {

    private Object owner;

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public Object getOwner() {
        return owner;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        setOwner(null);
    }
}
