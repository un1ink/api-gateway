package cn.un1ink.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author：un1ink
 * @date: 2023/5/23
 */

@SpringBootApplication
@Configurable
@EnableDubbo
public class ApiGatewayTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayTestApplication.class, args);
    }
}
