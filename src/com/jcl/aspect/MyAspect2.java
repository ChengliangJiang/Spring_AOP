package com.jcl.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyAspect2 implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //拦截方法
        System.out.println("开启事务");
        //放行
        Object obj=methodInvocation.proceed();

        System.out.println("提交事务");

        //返回
        return obj;
    }
}
