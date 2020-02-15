package com.demo.jvm;

public class MyTest7 {
    public static void main(String[] args)throws Exception {
        Class<?>clazz=Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader());
        Class<?>claszz2=Class.forName("com.demo.jvm.c");
        System.out.println(claszz2.getClassLoader());
    }
}
class c
{}