package com.xiefuzhong.community.service;



import com.xiefuzhong.community.entity.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {
//    User checkUserById(Integer id);

    Map<String, Object> register(User user) throws IllegalAccessException, SQLException;

    Map<String, Object> login(String username, String password, int expiredSeconds) throws IllegalAccessException, SQLException;

    void logout( String ticket) ;

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
