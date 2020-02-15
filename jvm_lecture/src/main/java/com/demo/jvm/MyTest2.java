package com.demo.jvm;

/**
 * 带有final的常量在编译阶段会存入到调用这个常量方法的类的常量池中，
 * 本质上，调用类并没有直接引用到定义常量的类，因此不会触发定义常量类的初始化
 * 注意：这里将常量存放到MyTest2的常量池中，之后MyTest2和MyParent2就没有任何关系了
 * 甚至可以把MyParent2删除
 */

/**
 * 助记符：
 * ldc表示将int，float或者是String类型的常量值从常量池中推送到栈顶
 * bipush表示将单字节（-128～127）的常量值推送至栈顶
 * sipush表示将短整形（-32768～32767）常量值推送至栈顶
 * iconst_1表示将整形1推送至栈顶（最多到5）
 */
public class MyTest2 {
    public static void main(String[] args) {
        System.out.println(MyParent2.str2);
    }
}
class MyParent2
{
    public static final String str2="hello world";
    static {
        System.out.println("MyParent2 static block");
    }
}