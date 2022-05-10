package com.atguigu.gulimall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 如何远程调用别的服务
 * 1）引入open-feign
 * 2）编写一个接口，告诉springcloud需要调用远程服务
 * 3)生命接口得每个方法都是调用哪个远程服务的那个请求
 * 4）开启远程调用功能@EnableFeignClients(basePackages = "com.atguigu.gulimall.member.feign")*/
@EnableFeignClients(basePackages = "com.atguigu.gulimall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}
