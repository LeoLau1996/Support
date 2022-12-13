package com.auto.apt_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ---------------------------------------------------------------------------------------------
 * 功能描述:
 * ---------------------------------------------------------------------------------------------
 * 时　　间: 2022/12/12
 * ---------------------------------------------------------------------------------------------
 * 代码创建: Leo
 * ---------------------------------------------------------------------------------------------
 * 代码备注:
 * ---------------------------------------------------------------------------------------------
 **/
// 标记的注解在编译时由编译器保留，但 Java 虚拟机(JVM)会忽略
@Retention(RetentionPolicy.CLASS)
// 修饰变量
@Target(ElementType.TYPE)
public @interface LayoutViewsGroupConfig {

    // 包名
    String packageName() default "";

    // 你项目的布局文件的绝对路径（例如：/Users/leolau/Documents/LeoWork/liveroom/app/src/main/res）
    String[] layoutPaths() default {};

}
