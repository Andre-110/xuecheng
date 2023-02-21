package com.xuecheng;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuqi
 * @version 1.0
 * @description 启动类
 * @date 2023-02-15 23:02
 */

//依赖，配置，扫描，注释，测试
@EnableSwagger2Doc
@SpringBootApplication
public class ContentApiApplication {
    public static void main(String[] args){
        SpringApplication.run(ContentApiApplication.class,args);
    }
}

