package com.demo.jvm;

/**
 * 当前类加载器（current classload）
 * 每个类都会使用自己的类加载器（加载自身的类加载器）来去加载其他类
 * 如果classX引用了classY那么calssX的类加载器就会尝试加载classY，前提是classY没有被加载
 *
 * 线程上线文类加载器（context classloader）
 * 线程上下文类加载器是从jdk1.2开始启用的，通过getContextClassLoader（）和setContextClassLoader（ClassLoader c1）分别
 * 用来获取和设置上下文类加载器
 * 如果没有通过setContextClassLoader（ClassLoader c1）来设置类加载器，那么将默认继承其父类的线程上下文类加载器。
 * java运行时初始上下文类加载器是系统类加载器。在线程中运行的代码可以通过这个类加载器获取类和资源
 * 线程上下文类加载器的重要性：
 * SPI（Serivce Provider Interface）
 * 父加载器可以使用当前线程Thread.currentThread().getContextClassLoader()所指定的classloader加载的类
 * 这就改变了父calssLoader不能使用子classLoader或者其他没有父子关系的classLoader加载的类的情况，即破坏了双亲委托模型
 *
 * 在双亲委托模型下，类加载是由下至上的，即下层的类加载器会委托上层来进行加载。但是对于SPI来说，有些接口是由java核心库来提供的
 * 因此使用的是启动类加载器，而接口的实现则是各个厂商提供的jar包如（mysql-connector-xxxx.jar）并放到classpath来实现，java的启动类加载器是不会加载不同来源的jar包，这样
 * 传统的类加载方法无法满足SPI需求。通过给当前线程提供线程上下文类加载器，就可以设置线程上下文类加载来实现对于接口类的加载。
 */
public class MyTest12 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Thread.class.getClassLoader());
    }
}
