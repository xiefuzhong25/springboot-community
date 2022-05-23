package com.xiefuzhong.community.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/5/22 0022 11:45
 */
@Service
//@Scope("request")
public class AtestService {

    public AtestService(){
        System.out.println("实例化AtestService");
    }

    @PostConstruct    //构造器之后调用，用来初始化数据
    public  void init(){
        System.out.println("初始化AtestService");
    }

    @PreDestroy    //销毁之前调用，用来释放资源
    public void  destroy(){
        System.out.println("销毁Atestservice");
    }
}
