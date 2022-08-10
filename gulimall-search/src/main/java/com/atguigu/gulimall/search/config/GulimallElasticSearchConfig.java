package com.atguigu.gulimall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lxl
 * @Date: 2022/08/08/18:45
 * @Description:
 * 1.导入配置
 * 2.编写配置，给容器中注入一个RestHightlevelClient
 * 3.参照API操作:https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-getting-started-maven.html
 */

@Configuration
public class GulimallElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient() {
        RestClientBuilder builder = null;
        builder = RestClient.builder(new HttpHost("192.168.56.10", 9200, "http"));

        RestHighLevelClient client = new RestHighLevelClient(builder);
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("localhost", 9200, "http"),
//                        new HttpHost("localhost", 9201, "http")));
        return client;
    }
}
