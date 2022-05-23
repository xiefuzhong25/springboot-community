package com.xiefuzhong.community;

import com.xiefuzhong.community.dao.AtestDao;
import com.xiefuzhong.community.service.AtestService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

    private  ApplicationContext  applicationcontext ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationcontext = applicationContext;
    }



    @Test
    public  void  testapplicationText(){
        System.out.println(applicationcontext);
    }

    @Test
    public  void  testDao(){
        AtestDao atestDao = applicationcontext.getBean(AtestDao.class);
        System.out.println(atestDao.select());
    }

    @Test
    public  void  testBeanManagement(){
       AtestService aService = applicationcontext.getBean(AtestService.class);
        System.out.println(aService);
    }

    @Test
    public  void  testBeanConfig(){
        SimpleDateFormat simpleDateFormat = applicationcontext.getBean(SimpleDateFormat.class);

        System.out.println(simpleDateFormat.format(new Date()));
    }

    @Autowired
//    @Qualifier("beanName")  指定实现类
    private AtestDao atestDao;
    @Test
    public  void  testDI(){

        System.out.println(atestDao);
    }



}
