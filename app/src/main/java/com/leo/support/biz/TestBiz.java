package com.leo.support.biz;

import android.os.Bundle;

import com.surgery.scalpel.base.LifeControlInterface;
import com.surgery.scalpel.base.biz.CommonLifeBiz;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/13
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public class TestBiz extends CommonLifeBiz {

    public TestBiz(LifeControlInterface lifeControlInterface) {
        super(lifeControlInterface);
    }

    public TestBiz(LifeControlInterface lifeControlInterface, Bundle savedInstanceState) {
        super(lifeControlInterface, savedInstanceState);
    }
}
