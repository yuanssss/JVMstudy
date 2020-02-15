package com.demo.bytecode;

/**
 * 方法的静态分派：
 *         Grandpa g1=new Father();
 *         g1的静态类型是Grandpa，而g1实际类型（实际指向的类型）是Father
 *         结论：变量的静态类型是不会发生变化的，而实际类型是可以发生变化的，实际类型在运行期可以确定
 */
public class MyTest4{
    //方法重载是一种静态行为，编译器就可以完全确定。Java中存在两种多态，重载和重写，重写是一种动态
    public  void test(Grandpa grandpa)
    {
        System.out.println("grandpa");
    }
    public  void test(Father father)
    {
        System.out.println("father");
    }
    public  void test(Son son)
    {
        System.out.println("son");
    }
    public static void main(String[] args) {
        Grandpa g1=new Father();
        Grandpa g2=new Son();
        MyTest4 myTest4=new MyTest4();
        myTest4.test(g1);
        myTest4.test(g2);
    }
}
class Grandpa
{}
class Father extends Grandpa
{}
class Son extends Father
{}
