package com.xiefuzhong.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
    //声明了这个配置类，就可以使用@Async注解了
}
