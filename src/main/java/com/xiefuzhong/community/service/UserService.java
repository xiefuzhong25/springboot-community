package com.xiefuzhong.community.service;


import com.xiefuzhong.community.dao.UserMapper;
import com.xiefuzhong.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户id 查询用户信息
     * @param id
     * @return
     */
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

}
