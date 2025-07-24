package com.itheima.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.itheima.mp.mapper")
@SpringBootApplication
public class MpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpDemoApplication.class, args);

        System.out.println("\nSpring Cloud Learn01 Start Success, serve port: 8080");
    }

}

