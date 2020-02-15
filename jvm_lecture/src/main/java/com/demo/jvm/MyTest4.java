package com.demo.jvm;

/**
 * 对于数组实例来说，其类型是在jvm运行中动态生成的，表示为[Lcom.demo.jvm.MyParent4;这种形式
 * 动态生成的类型其父类型就是object
 * 对于数组来说，javaDoc经常将构成数组的元素称为component，实际上就是将数组降低一个纬度的类型
 * 助记符：
 * anewarray：表示创建一个引用类型的（如类、接口、数组）数组，并将其引用压入栈顶
 * newarray：表示创建一个指定的原始类型（如int、float、char等）的数组，并将其引用值压入栈顶
 */
public class MyTest4 {
    public static void main(String[] args) {
//        MyParent4 myParent4=new MyParent4();
//        MyParent4 myParent5=new MyParent4();//第二次主动使用不会打印
        MyParent4[]myParent4s=new MyParent4[1];
        System.out.println(myParent4s.getClass());
    }
}
class MyParent4
{
    static {
        System.out.println("MyParent4 static block");
    }
}
