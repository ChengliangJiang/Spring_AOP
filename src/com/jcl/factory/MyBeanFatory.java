package com.jcl.factory;

import com.jcl.aspect.MyAspect;
import com.jcl.service.IUserService;
import com.jcl.service.IUserServiceImpl;
import com.jcl.service.StudentService;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyBeanFatory {

    //jdk实现的代理
    public static IUserService createUserService(){
        //1创建目标对象
        IUserService userService=new IUserServiceImpl();

        //2申明切面类
        MyAspect aspect=new MyAspect();

        //3把切面类两个方法应用到目标类
        //3.1生成jdk代理,拦截方法
        /*newProxyInstance
                (ClassLoader loader,加载当前类
                Class<?>[] interfaces,接口，接口的方法会被拦截
                InvocationHandler h)*/
        IUserService userServiceProxy=(IUserService) Proxy.newProxyInstance(MyBeanFatory.class.getClassLoader(),
                        userService.getClass().getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                                //开启事务
                                aspect.before();

                                //返回值是业务方法的返回值
                                Object obj=method.invoke(userService,args);
                                System.out.println("拦截的返回值 "+obj);

                                //提交事务
                                aspect.after();

                                return obj;
                            }
                        });


        return userServiceProxy;
    }

    //cglib
    public static StudentService createStudentService2(){
        //1创建目标对象
        StudentService studentService=new StudentService();

        //2申明切面类
        MyAspect aspect=new MyAspect();

        //3增强对象
        Enhancer enhancer=new Enhancer();

        //设置父类
        enhancer.setSuperclass(studentService.getClass());

        //设置拦截回调
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                /**
                 * o代理对象,是真是对象的子类
                 *objects：参数
                 */
                aspect.before();

                Object obj=method.invoke(studentService,objects);

                aspect.after();

                return obj;
            }
        });

        //创建代理对象
        StudentService service=(StudentService) enhancer.create();

        return service;
    }
}
