package com.leo.support.View.Presenter;

import com.leo.support.View.Listener.MainListener;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2019-08-05
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 刘桂安
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MainPresenter {
    private MainListener listener;

    public MainPresenter(MainListener listener) {
        this.listener = listener;
    }

    public void select1(){
        listener.onSelectA();
    }
    public void select2(){
        listener.onSelectB();
    }
    public void select3(){
        listener.onSelectC();
    }
    public void select4(){
        listener.onSelectD();
    }
}
