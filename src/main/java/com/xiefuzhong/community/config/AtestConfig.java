package com.xiefuzhong.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/5/22 0022 13:08
 */

@Configuration
public class AtestConfig {

    @Bean
    public SimpleDateFormat  simpleDateFormat(){
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
