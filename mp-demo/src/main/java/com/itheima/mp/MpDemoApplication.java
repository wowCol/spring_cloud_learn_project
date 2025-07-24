package com.itheima.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@MapperScan("com.itheima.mp.mapper")
@SpringBootApplication
public class MpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpDemoApplication.class, args);
    }

    @EventListener
    public void onAppReady(ApplicationReadyEvent event) {
        ServerProperties serverProperties = event.getApplicationContext().getBean(ServerProperties.class);
        Integer port = serverProperties.getPort();
        System.out.println("\nSpring Cloud Learn01 Start Success, serve port: " + port);
    }

}

