package com.xiefuzhong.community.service;



import com.xiefuzhong.community.entity.LoginTicket;
import com.xiefuzhong.community.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> login(String username, String password, int expiredSeconds) throws IllegalAccessException, SQLException;

    void logout( String ticket) ;

    Map<String, Object> register(User user) throws IllegalAccessException, SQLException;

    LoginTicket  findLoginTicket(String ticket);

    User findUserById(int userId);

    int updateHeader(int id, String headerUrl);

    User findUserByName(String username);

    public Collection<? extends GrantedAuthority>  getAuthorities(int userId);


}
