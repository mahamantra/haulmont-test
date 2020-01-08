package com.example.demo7;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;

@SpringBootApplication


public class Demo7Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo7Application.class, args);

        String script = "init.sql";
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            new ScriptRunner(DriverManager.getConnection(
                    "jdbc:hsqldb:file:testDB", "sa", "123"))
                    .runScript(new BufferedReader(new FileReader(script)));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
