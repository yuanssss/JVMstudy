package com.demo.jvm;

import java.io.IOException;
import java.io.InputStream;

public class MyTest11 {
    public static void main(String[] args) throws Exception{
        ClassLoader myLoader=new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try{
                    String filename=name.substring(name.lastIndexOf(".")+1)+".class";
                    InputStream is=getClass().getResourceAsStream(filename);
                    if (is==null)
                    {
                        return super.loadClass(name);
                    }
                    byte[]b=new byte[is.available()];
                    is.read(b);
                    return defineClass(name,b,0,b.length);
                }
                catch (IOException e)
                {
                    throw new ClassNotFoundException(name);
                }

            }
        };
        Object obj=myLoader.loadClass("com.demo.jvm.MyTest11").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof  com.demo.jvm.MyTest11);


    }
}
