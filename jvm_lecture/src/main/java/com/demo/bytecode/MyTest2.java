package com.demo.bytecode;

public class MyTest2 {
    String str = "welcome";
    private int x = 5;
    public static Integer in = 10;

    public static void main(String[] args) {
        MyTest2 myTest2 = new MyTest2();
        myTest2.setX(10);
        in=20;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void test(String str)
    {
        synchronized (str)
        {
            System.out.println("hello");
        }
    }
}
