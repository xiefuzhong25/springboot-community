package com.xiefuzhong.community.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/6/7 0007 20:33
 */
@Configuration
public class EsConfig {

    @Value("${spring.elasticsearch.uris}")
    private String esUrl;

    //localhost:9200 写在配置文件中,直接用 <- spring.elasticsearch.uris
    @Bean
    RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esUrl)//elasticsearch地址
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}
