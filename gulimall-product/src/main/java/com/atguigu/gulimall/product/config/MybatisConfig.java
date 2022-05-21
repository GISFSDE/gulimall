package com.atguigu.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lxl
 * @Date: 2022/05/21/23:19
 * @Description:
 */
//配置类
@Configuration
//开启事务
@EnableTransactionManagement
@MapperScan("com.atguigu.gulimall.product.dao")
public class MybatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        {
            PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        设置请求的页面大于最大页后操作，true调回首页吗，false继续请求,默认false
            paginationInterceptor.setOverflow(true);
//            设置最大单页限数量，默认500条，-1不受限制
            paginationInterceptor.setLimit(1000);
            return paginationInterceptor;
        }
    }
}

