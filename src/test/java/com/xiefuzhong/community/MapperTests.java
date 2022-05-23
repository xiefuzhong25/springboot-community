package com.xiefuzhong.community;

import com.xiefuzhong.community.dao.UserMapper;
import com.xiefuzhong.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/5/22 0022 17:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public  void  testSelectUser(){
        User user = userMapper.selectById(101);
        User user2 = userMapper.selectByName("liubei");
        User user3 = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
        System.out.println(user2);
        System.out.println(user3);
    }


}
