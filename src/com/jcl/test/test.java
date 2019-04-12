package com.jcl.test;

import com.jcl.service.IUserService;
import com.jcl.factory.MyBeanFatory;
import com.jcl.service.StudentService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test  {

    @Test
    public void test() throws Exception {
        //手动实现AOP编程，使用JDK代理

        IUserService userService=MyBeanFatory.createUserService();

        userService.deleteUser();

    }
    @Test
    public void test2(){
        //使用cglib代理
        StudentService service=MyBeanFatory.createStudentService2();
        service.delete();
    }

    @Test
    public void test3(){
        //获取spring容器中的代理对象
        ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
        IUserService userService=(IUserService)context.getBean("serviceProxy");
        userService.deleteUser();
    }
    @Test
    public void test1() throws Exception {

        //获取Spring容器中代理对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans1.xml");

        IUserService userService = (IUserService) context.getBean("userService");

        userService.deleteUser();

    }
}
