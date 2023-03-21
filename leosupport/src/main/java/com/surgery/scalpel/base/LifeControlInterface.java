package com.surgery.scalpel.base;

import com.surgery.scalpel.base.biz.CommonLifeBiz;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2021/12/29
 * ---------------------------------------------------------------------------------------------
 * 代码创建: 1613-3
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
public interface LifeControlInterface {

    //添加生命周期监听
    void addLifeCallBackList(CommonLifeBiz biz);

    //删除生命周期监听(不要在for循环中使用这个删除)
    void removeLifeCallBackList(CommonLifeBiz biz);

    <B extends CommonLifeBiz> B obtainBiz(Class<B> bizClass);

    <B extends CommonLifeBiz> B obtainBiz(String bizTag, Class<B> bizClass);

}
