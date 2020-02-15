package com.demo.jvm;


/**
 * 准备阶段的重要意义
 */
public class MyTest6 {
    public static void main(String[] args) {
        Singleton singleton=Singleton.getInstance();//调用Singleton的静态方法，为主动使用
        System.out.println("counter1:  "+Singleton.counter1);
        System.out.println("counter2   "+Singleton.counter2);

    }
}
//首先是准备阶段，给类中默认的值进行赋初值
//对Singleton进行初始化
class Singleton {
    public static int counter1;//默认初值为0，初始化为0
    private static Singleton singleton = new Singleton();//默认初值为null，初始化为构造函数的返回值1，0

    private Singleton() {
        counter1++;//1
        counter2++;//1
        System.out.println(counter1);
        System.out.println(counter2);
    }
    public static int counter2 = 0;//默认初值为0，初始化时赋与了0

    public static Singleton getInstance() {
        return singleton;
    }
}