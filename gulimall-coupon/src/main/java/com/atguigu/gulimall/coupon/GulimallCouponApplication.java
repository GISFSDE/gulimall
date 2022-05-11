package com.atguigu.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * 1.如何使用Nacos作为配置中心统一管理配置
 *
 * 1)引入依赖
 *     <dependency>
 *         <groupId>com.alibaba.cloud</groupId>
 *         <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *     </dependency>
 *
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
 *
 * 2.一些细节
 * 1）命名空间：配置隔离
 * 默认public（保留空间）；默认新增的所有配置都在public空间
 * (1)示例1：开发，测试，生产,利用命名空间做环境隔离
 * 需要在bootsrap.properties配置命名空间
 * spring.cloud.nacos.config.namespace=ed3be8e2-39d5-46b9-851d-f1e13dd0fe59
 * (2)示例2：每个微服务之间互相隔离配置，每一个微服务都创建自己的命名空间，之家在自己命名空间下的所有配置
 * 2）配置集：所有配置的集合
 * 3）配置集ID：Data ID ：类似文件名
 * 4）配置分组:默认所有配置集都属于DEFAULT_GROUP
 * 每个微服务创建自己的命名空间，使用配置分组区分环境，dev,test,prod
 *
 * 3.
 * */
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
