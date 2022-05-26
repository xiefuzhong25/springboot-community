package com.xiefuzhong.community.service;



import com.xiefuzhong.community.entity.LoginTicket;
import com.xiefuzhong.community.entity.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {

    Map<String, Object> login(String username, String password, int expiredSeconds) throws IllegalAccessException, SQLException;

    void logout( String ticket) ;

    Map<String, Object> register(User user) throws IllegalAccessException, SQLException;

    LoginTicket  findLoginTicket(String ticket);

    User findUserById(int userId);

    void updateHeader(int id, String headerUrl);

//    User checkUserByIdCodeActivate(Integer id, String codeActivate);

//    Integer reviseStatusById(Integer id, String status);

//    TicketLogin checkTicketLoginByUsernameTicketStatus(String username, String ticket, String status);

//    UserLogined checkUserLoginedByUsername(String username);

//    Integer reviseStatusTicketLoginById(Integer id, String status);

//    Integer reviseUrlHeaderByUsername(Integer id, String urlHeader);

//    Map<String, Object> revisePassword(Integer id, String passwordOld, String passwordNew) throws IllegalAccessException, SQLException;

//    Map<String, Object> reviseInformation(UserLogined userLogined) throws IllegalAccessException, SQLException;

//    UserLogined checkUserLoginedById(Integer id);

//    User checkUserByUsername(String username);

//    Integer revisePasswordByEmail(String email, String password, String random);

//    User checkUserByEmail(String email);

//    Map<String, Object> sendCaptchaEmail(String email);
}
