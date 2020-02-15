package com.demo.bytecode;

/**
 * 方法的动态分派：
 * 动态分派涉及一个重要概念：方法接收者。
 * invokevirtual字节码指令的多态查找流程
 *
 */
public class MyTest5 {
    public static void main(String[] args) {
        Fruit apple=new Apple();
        Fruit orange=new Orange();
        apple.test();
        orange.test();
        apple=new Orange();
        apple.test();
    }
}
class Fruit
{
    public void test()
    {
        System.out.println("Fruit");
    }
}
class Apple extends Fruit
{
    @Override
    public void test()
    {
        System.out.println("apple");
    }
}
class Orange extends Fruit
{
    @Override
    public void test()
    {
        System.out.println("Orange");
    }
}