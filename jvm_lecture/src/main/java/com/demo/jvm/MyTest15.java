package com.demo.jvm;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyTest15 {
    public static void main(String[] args) throws Exception{
//        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection("");
    }
}
