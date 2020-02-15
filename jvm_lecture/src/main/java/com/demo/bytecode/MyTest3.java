package com.demo.bytecode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

public class MyTest3 {
    public void test()
    {
        try {
            InputStream in=new FileInputStream("test.txt");
            ServerSocket ss=new ServerSocket(9999);
            ss.accept();
        }
        catch (FileNotFoundException e)
        {}
        catch (IOException e)
        {}
        catch (Exception e)
        {}
        finally {
            System.out.println("finally");
        }

    }}
