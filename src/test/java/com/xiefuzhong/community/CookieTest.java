package com.xiefuzhong.community;

import com.xiefuzhong.community.util.CommunityUtil;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/5/24 0024 10:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CookieTest {

    @RequestMapping(path = "/cookie/set",method = RequestMethod.GET)
    @ResponseBody
    public  String  setCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie生效范围
        cookie.setPath("/community");
        //设置cookie的生存时间s:设置了会存到硬盘
        cookie.setMaxAge(60*10);
        //发送cookie
        response.addCookie(cookie);
        return "set cookie";
    }

    @RequestMapping(path = "/cookie/get",method = RequestMethod.GET)
    @ResponseBody
    public  String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return "get cookie";
    }




}
