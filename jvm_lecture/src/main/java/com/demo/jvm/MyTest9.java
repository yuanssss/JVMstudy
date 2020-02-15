package com.demo.jvm;
//调用classLoader类的loadClass方法加载一个类，并不是对类的主动使用，不会导致初始化
public class MyTest9 {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader=ClassLoader.getSystemClassLoader();
        Class<?>clazz=classLoader.loadClass("com.demo.jvm.Cl");
        System.out.println(clazz);
        System.out.println("-------");
        clazz=Class.forName("com.demo.jvm.Cl");
        System.out.println(clazz);
    }
}
class Cl{
    static {
        System.out.println("Cl static block");
    }
}