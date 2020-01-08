package com.example.demo7;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication


public class Demo7Application {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

//        Class.forName("org.hsqldb.jdbc.JDBCDriver");
//
//        String script = "schema.sql";
//        try {
//            new ScriptRunner(DriverManager.getConnection(
//                    "jdbc:hsqldb:file:testDB", "sa", "123"))
//                    .runScript(new BufferedReader(new FileReader(script)));
//        } catch (Exception e) {
//            System.err.println(e);
//        }


        SpringApplication.run(Demo7Application.class, args);


    }

}
