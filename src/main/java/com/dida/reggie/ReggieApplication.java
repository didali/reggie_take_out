package com.dida.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j //lombok提供的注解，可以输出日志
@SpringBootApplication
@ServletComponentScan //配置了这个注解才能扫描到 filter
//@MapperScan("com.dida.reggie.mapper")
@EnableTransactionManagement
@EnableCaching //开启springCache的缓存注解
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        //输出日志
        log.info("项目启动成功...");
    }
}
