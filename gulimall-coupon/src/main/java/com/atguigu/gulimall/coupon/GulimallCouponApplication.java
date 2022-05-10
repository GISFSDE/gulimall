package com.atguigu.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * 1)引入依赖
 *     <dependency>
 *         <groupId>com.alibaba.cloud</groupId>
 *         <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *     </dependency>
 * 2）创建bootstrap.properties
 * spring.application.name=gulimall-coupon
 * spring.cloud.nacos.config.server-addr=127.0.0.1:8848
 * 如果未生效添加bootstrap依赖
 * <--解决bootstrap.properties不生效 com.alibaba.nacos.api.exception.NacosException: java.lang.reflect.InvocationTargetException-->
 *     <dependency>
 *         <groupId>org.springframework.cloud</groupId>
 *         <artifactId>spring-cloud-starter-bootstrap</artifactId>
 *         <version> 3.0.5</version>
 *     </dependency>
 *
 * 3）Nacos配置中心添加数据集gulimall-coupon.properties。默认是应用名。properties
 * 4）给gulimall-coupon.properties添加配置
 * 5）动态获取配置
 * controller上添加@RefreshScope动态获取配置
 * @Value（${配置项名}）
 * 配置中心的配置优先
 * */
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
