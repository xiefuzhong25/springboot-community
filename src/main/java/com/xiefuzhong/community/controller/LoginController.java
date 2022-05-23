package com.xiefuzhong.community.controller;

import com.xiefuzhong.community.dao.UserMapper;
import com.xiefuzhong.community.entity.User;
import com.xiefuzhong.community.service.impl.UserServiceImpl;

import com.xiefuzhong.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class LoginController implements CommunityConstant {

    @Autowired
    private UserServiceImpl userService;

//    @Autowired
//    private Producer kaptcheProducer;
//
//    @Autowired
//    private RedisTemplate redisTemplate;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 去到注册页
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {
        return "site/register";
    }

    /**
     * 去到登录页
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model) {
        return "site/login";
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public  String register(Model model,User user){
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()){
            model.addAttribute("msg","注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活！");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "/site/register";
        }
    }

    ////http://localhost:8080/community/activate/101/code
    @RequestMapping(path = "/activate/{userId}/{code}",method = RequestMethod.GET)
    public   String activation(Model model,@PathVariable("userId") int userId,@PathVariable("code") String code){
        int result = userService.activation(userId,code);
        if (result == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功，账户可以正常使用！");
            model.addAttribute("target","/login");
        }else if (result == ACTIVATION_REPEAT){
            model.addAttribute("msg","无效操作，账户重复激活！");
            model.addAttribute("target","/index");
        }else {
            model.addAttribute("msg","激活失败，账户激活码不正确！");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";

    }

}