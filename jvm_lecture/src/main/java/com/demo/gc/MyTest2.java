package com.demo.gc;

/**
 * -XX:PretenureSizeThreshold=4194304 当创建的对象超过4m的大小时，对象直接在老年代创建
 * 必须指定SerialGC时才能起作用
 */
public class MyTest2 {
    public static void main(String[] args) {
        int size=1024*1024;
        byte[]myAlloc=new byte[8*size];
    }
}
