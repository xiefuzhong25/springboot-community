package com.xiefuzhong.community.service;



import com.xiefuzhong.community.entity.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {
//    User checkUserById(Integer id);

    Map<String, Object> register(User user) throws IllegalAccessException, SQLException;

//    User checkUserByIdCodeActivate(Integer id, String codeActivate);

//    Integer reviseStatusById(Integer id, String status);

//    Map<String, Object> login(String username, String password, int daysExpired) throws IllegalAccessException, SQLException;

//    TicketLogin checkTicketLoginByUsernameTicketStatus(String username, String ticket, String status);

//    UserLogined checkUserLoginedByUsername(String username);

//    Integer reviseStatusTicketLoginById(Integer id, String status);

//    Map<String, Object> logout(String username, String ticket) throws SQLException;

//    Integer reviseUrlHeaderByUsername(Integer id, String urlHeader);

//    Map<String, Object> revisePassword(Integer id, String passwordOld, String passwordNew) throws IllegalAccessException, SQLException;

//    Map<String, Object> reviseInformation(UserLogined userLogined) throws IllegalAccessException, SQLException;

//    UserLogined checkUserLoginedById(Integer id);

//    User checkUserByUsername(String username);

//    Integer revisePasswordByEmail(String email, String password, String random);

//    User checkUserByEmail(String email);

//    Map<String, Object> sendCaptchaEmail(String email);
}
