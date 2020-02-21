package com.demo.gc;

public class MyTest4 {
    public static void main(String[] args)throws InterruptedException {
        byte[]byte1=new byte[512*1024];
        byte[]byte2=new byte[512*1024];
        myGc();
        Thread.sleep(1000);
        System.out.println("111111");
        myGc();
        Thread.sleep(1000);
        System.out.println("222222");
        myGc();
        Thread.sleep(1000);
        System.out.println("333333");
        myGc();
        Thread.sleep(1000);
        System.out.println("4444444");

        byte[]byte3=new byte[1024*1024];
        byte[]byte4=new byte[1024*1024];
        byte[]byte5=new byte[1024*1024];

        myGc();
        Thread.sleep(1000);
        System.out.println("55555555");

        myGc();
        Thread.sleep(1000);
        System.out.println("6666666");

        System.out.println("hello world");

    }
    private static void myGc()
    {
        for (int i=0;i<40;i++)
        {
            byte[]bytesArray=new byte[1024*1024];
        }
    }
}
