package com.xiefuzhong.community.controller;

import com.google.code.kaptcha.Producer;
import com.xiefuzhong.community.dao.UserMapper;
import com.xiefuzhong.community.entity.User;
import com.xiefuzhong.community.service.impl.UserServiceImpl;

import com.xiefuzhong.community.util.CommunityConstant;
import com.xiefuzhong.community.util.CommunityUtil;
import com.xiefuzhong.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
public class LoginController implements CommunityConstant {

    private  static  final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private Producer kaptcheProducer;

    @Autowired
    private RedisTemplate redisTemplate;

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

    //点击邮箱文件激活账号 
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

   //生成动态验证码
    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)
    public  void getKaptcha(HttpServletResponse response /*, HttpSession session */)
    {
        //生成验证码
        String text = kaptcheProducer.createText();
        BufferedImage image = kaptcheProducer.createImage(text);
        //将验证码存到session
//        session.setAttribute("kaptcha",text);
        //验证码的归属==============================================重构开始===========
        String kaptchaOwner = CommunityUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner",kaptchaOwner);
        cookie.setMaxAge(60);
        cookie.setPath(contextPath);
        //将cookie发送给客户端
        response.addCookie(cookie);
        //将验证码存入redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey,text,60, TimeUnit.SECONDS);
        //==========================================================重构结束=====

        response.setContentType("image/png");

        try {
            //将该图片输出到浏览器
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            logger.error("响应码验证失败：" + e.getMessage());
        }
    }

    //验证登录
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String username, String password, String code, boolean rememberme,
                        Model model /*, HttpSession session*/, HttpServletResponse response,@CookieValue("kaptchaOwner") String kaptchaOwner) {
        // 检查验证码
       // String kaptcha = (String) session.getAttribute("kaptcha");
        //===========重构开始===========
        String kaptcha = null;
        if (StringUtils.isNotBlank(kaptchaOwner)){
            String  redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }
        //=============重构结束



        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            model.addAttribute("codeMsg", "验证码不正确!");
            return "/site/login";
        }

        // 检查账号,密码
        //是否勾选了记住密码：保存时长不一样
        int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            //将cookie发送给浏览器
            response.addCookie(cookie);
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }

}