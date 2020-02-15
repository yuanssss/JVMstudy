package com.demo.bytecode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        RealSubject realSubject=new RealSubject();
        InvocationHandler ds=new DynamicSubject(realSubject);
        Class<?>cls=realSubject.getClass();
        Subject subject=(Subject) Proxy.newProxyInstance(cls.getClassLoader(),cls.getInterfaces(),ds);
        subject.request();
    }
}
