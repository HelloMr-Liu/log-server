package org.king2.log.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:配置ES客户端
 * @author 刘梓江
 * @Date 2021/3/19 13:51
 */
@Configuration
public class ElasticSearchClientConfiguration {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("www.king2.cn",9200,"http"))
        );
        return client;
    }
}
