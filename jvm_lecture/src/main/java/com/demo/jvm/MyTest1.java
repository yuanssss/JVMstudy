package com.demo.jvm;

/**
 * 当输出为MyChild1.str时，为主动使用MyParent1，符合主动使用调用一个类的子类
 * 而对于MyChild1则为被动使用所以不会被初始化，故静态代码块不会被加载
 * 当输出为MyChild1.str2时，则既主动使用了MyParent1也主动使用了MyChild1
 * 当一个类被初始化时，要求其父类全部初始化
 * -XX:+TraceClassLoading,用于追踪类的加载信息并打印出来
 * -XX:+<option>表示开启option选项
 * -XX:-<option>表示关闭option选项
 * -XX:<option>=<value>,表示将option值设置为value
 */
public class MyTest1 {
    public static void main(String[] args) {

        System.out.println(MyChild1.str2);
    }
}
class MyParent1
{
    public  static  String str="hello world";
    static {
        System.out.println("MyParent static block");
    }
}

class MyChild1 extends MyParent1
{
    public static String str2="welcome";
    static {
        System.out.println("MyChild static block");
    }
}