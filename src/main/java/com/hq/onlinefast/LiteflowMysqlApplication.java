package com.hq.onlinefast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan("com.hq.onlinefast.mapper")
public class LiteflowMysqlApplication {
    //获取应用程序上下文环境
    public static ApplicationContext applicationContext;
    public static void main(String[] args) {

        applicationContext = SpringApplication.run(LiteflowMysqlApplication.class, args);
    }

}
