package com.demo.gc;

/**
 * MaxTenuringThreshold作用：在可以自动调节对象晋升（Promote）到老年代阈值的GC中，设置该阈值的最大值
 * 该参数的默认值为15，CMS中的默认值6，G1中默认为15
 */
public class MyTest3 {
    public static void main(String[] args) {
        int  size=1024*1024;
        byte[]myAlloc1=new byte[2*size];
        byte[]myAlloc2=new byte[2*size];
        byte[]myAlloc3=new byte[3*size];
        byte[]myAlloc4=new byte[3*size];

        System.out.println("hello world");
    }
}
