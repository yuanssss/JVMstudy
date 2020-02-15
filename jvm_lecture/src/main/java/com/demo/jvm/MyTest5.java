package com.demo.jvm;

/**
 * 当一个接口在初始化时，并不要求其父接口都完成初始化
 * 只有当真正使用到接口的时候（如引用接口中所定义的常量的时候），才会初始化
 */
public class MyTest5 {
    public static void main(String[] args) {
        System.out.println(MyChild5.b);
    }
}
interface MyParent5
{
    public static  int a=5;
    public static Thread thread=new Thread()
    {
        {
            System.out.println("Myparent5 block");
        }
    };
}
class MyChild5 implements MyParent5
{
    public static int b=6;
}