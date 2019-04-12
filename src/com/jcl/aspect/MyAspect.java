package com.jcl.aspect;

/**
 * 切面类:增强代码 与 切入点 的结合
 */
public class MyAspect {

    public void before(){
        System.out.println("开启事务");
    }
    public void after(){
        System.out.println("提交事务");
    }

}
