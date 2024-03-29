package com.xiefuzhong.community.service.impl;


import com.xiefuzhong.community.dao.LoginTicketMapper;
import com.xiefuzhong.community.dao.UserMapper;
import com.xiefuzhong.community.entity.LoginTicket;
import com.xiefuzhong.community.entity.User;
import com.xiefuzhong.community.service.UserService;
import com.xiefuzhong.community.util.CommunityConstant;
import com.xiefuzhong.community.util.CommunityUtil;
import com.xiefuzhong.community.util.MailClient;
import com.xiefuzhong.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService , CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;
//    @Autowired
//    private LoginTicketMapper  loginTicketMapper;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 根据用户id 查询用户信息
     * @param id
     * @return
     */
    public User findUserById(int id) {
       // return userMapper.selectById(id);
        User user = getCache(id);
        if (user == null){
           user = initCache(id);
        }
        return  user;
    }




    /**
     * 注册账号
     * @param user
     * @return
     * @throws IllegalAccessException
     * @throws SQLException
     */
    @Transactional
    @Override
    public Map<String, Object> register(User user)  {
        /*参数不为空*/
        Map<String, Object> map = new HashMap<>();
       if (user == null){
           throw  new  IllegalArgumentException("参数不能为空");
       }
       if (StringUtils.isBlank(user.getUsername())){
           map.put("usernameMsg","账号不能为空！");
           return  map;
       }
        if (StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空！");
            return  map;
        }
        if (StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空！");
            return  map;
        }
        //验证账号
        User u = userMapper.selectByName(user.getUsername());
        if (u != null){
            map.put("usernameMsg","账号已经存在！");
            return  map;
        }
        //验证邮箱
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null){
            map.put("emailMsg","该邮箱已经被注册！");
        }
        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //发送激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        //http://localhost:8080/community/activate/101/code
        String url = domain + contextPath + "/activate/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        //通过TemplateEngine 和Context 的配合，我们可以使用thymeleaf模版来生产html文件
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "账号激活", content);

        return map;
    }




    /**
     * 点击邮件激活账户
     * @param userId
     * @param code
     * @return
     */
    public int activation(int userId, String code){
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1 ){
            //重复激活
            return  ACTIVATION_REPEAT;
        }else if (user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            clearCache(userId);
            //激活成功
            return  ACTIVATION_SUCCESS;
        }else {
            //激活失败
            return  ACTIVATION_FAILURE;
        }
    }

    /**
     * 登录验证
     * @param username
     * @param password
     * @param expiredSeconds
     * @return
     * @throws IllegalAccessException
     * @throws SQLException
     */
    @Override
    public Map<String, Object> login(String username, String password, int expiredSeconds)  {
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在!");
            return map;
        }

        // 验证状态
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活!");
            return map;
        }

        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确!");
            return map;
        }

        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        //重构开始=======将它存到redis中================================================
        //   loginTicketMapper.insertLoginTicket(loginTicket);
        String  redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        //存到redis中
        redisTemplate.opsForValue().set(redisKey,loginTicket);
        //重构结束=============================================================

        //浏览器只需要记录这个凭证：这个表有点类似于session功能, 用来保存用户登录信息凭证
        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    //退出登录
    @Override
    public void logout( String ticket){
        //loginTicketMapper.updateStatus(ticket, 1);
        String  redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket  loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey,loginTicket);

    }

    //通过ticket查询出一条ticket数据
    @Override
    public LoginTicket findLoginTicket(String ticket) {
//        return loginTicketMapper.selectByTicket(ticket);
        String  redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    //更新用户账号头像
    @Override
    public int updateHeader(int userId, String headerUrl) {
        //        return userMapper.updateHeader(userId, headerUrl);
        int  rows = userMapper.updateHeader(userId, headerUrl);
        clearCache(userId);
        return  rows;
    }

    @Override
    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }

    //=========redis重构user开始=========
    //1.优先从缓存中取值
    private User  getCache(int userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }
    //2.取不到时初始化缓存数据
    private User initCache(int userId){
        User user = userMapper.selectById(userId);
        String  redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey,user,60 * 60, TimeUnit.SECONDS);//一个小时过期
        return  user;
    }
    // 3.数据变更时清除缓存数据
    private void clearCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }
    //=====重构结束==============================

    //获得用户权限（使用security需要）
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        {
            User  user = this.findUserById(userId);
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    switch (user.getType()){
                        case 1:
                            return  AUTHORITY_ADMIN;
                        case 2:
                            return AUTHORITY_MODERATOR;
                        default:
                            return AUTHORITY_USER;
                    }
                }
            });
            return  list;
        }
    }
}
