package com.demo.memory;

/**
 * 死锁代码示例
 * 线程A拿到类A的锁，锁定B类的锁
 * 线程B拿到类B的锁，锁定A类的锁
 *"Thread-B":
 *   waiting to lock monitor 0x00007fdd6200fca8 (object 0x000000076aed9170, a java.lang.Class),
 *   which is held by "Thread-A"
 * "Thread-A":
 *   waiting to lock monitor 0x00007fdd6200fd58 (object 0x000000076b020bb8, a java.lang.Class),
 *   which is held by "Thread-B"
 */
public class MyTest3 {
    public static void main(String[] args) {
       new Thread(()->A.method(),"Thread-A").start();
       new Thread(()->B.method(),"Thread-B").start();
       try {
           Thread.sleep(40000);
       }
       catch (InterruptedException e)
       {e.printStackTrace();}
    }
}

class A {
    public static synchronized void method() {
        System.out.println("Method from A");
        try {
            Thread.sleep(5000);
            B.method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class B {
    public static synchronized void method() {
        System.out.println("Method from B");
        try {
            Thread.sleep(5000);
            A.method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

