package com.demo.jvm;

public class MyTest8 {
    static {
        System.out.println("Mytest8 static block");
    }
    public static void main(String[] args) {
        System.out.println(MyChild8.b);
    }
}
class MyParent8{
static int x=4;
static {
    System.out.println("MyParent static block");
}
}
class MyChild8 extends MyParent8
{
    static int b=9;
    static
    {
        System.out.println("MyChild9 static block");
    }
}