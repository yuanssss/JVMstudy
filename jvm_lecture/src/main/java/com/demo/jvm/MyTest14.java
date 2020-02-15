package com.demo.jvm;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 线程的上下文类加载器的一般使用模式（获取 - 使用 - 还原）
 * ClassLoader classLoader = this.thread.getContextClassLoader();
 * try
 * {
 * Thread.currentThread().setContextClassLoader(Target1);//设置Target1我想使用的类加载器
 * myMethod();//获取Target1这个类加载器
 * }
 * finally{
 * Thread.currentThread().setContextClassLoader(classLoader);//改回原来的classLoader还原
 * }
 *
 * myMethod（）则调用了Thread.thread.getContextClassLoader();获取当前线程的类加载器来去做某些事情
 *
 * 当高层提供了统一接口让低层去实现，同时又要在高层实例话或者加载低层的类时，就必须通过线程上下文类加载器来帮助高层的classLoader
 */
public class MyTest14 {
    public static void main(String[] args) {
        Thread.currentThread().setContextClassLoader(MyTest14.class.getClassLoader().getParent());
        ServiceLoader<Driver>serviceLoader=ServiceLoader.load(Driver.class);
        Iterator<Driver>iterator=serviceLoader.iterator();
        while (iterator.hasNext())
        {
            Driver driver=iterator.next();
            System.out.println("Driver  :"+driver.getClass()+"   load  :"+driver.getClass().getClassLoader());
        }
        System.out.println("当前线程的上下文类加载器   ："+Thread.currentThread().getContextClassLoader());
        System.out.println("ServiceLoader的类加载器   ："+ServiceLoader.class.getClassLoader());
    }
}
