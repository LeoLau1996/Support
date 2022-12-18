package com.leo.support.view;

import com.surgery.scalpel.base.data.ObservableData;
import com.surgery.scalpel.model.CommonViewModel;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/18
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class MainViewModel extends CommonViewModel {

    private ObservableData<String> name;

    public ObservableData<String> getName() {
        return name;
    }

    public void setName(String name) {
        if (this.name == null) {
            this.name = new ObservableData<>(getOwner());
        }
        this.name.setData(name);
    }

}
