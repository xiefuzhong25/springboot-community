package com.xiefuzhong.community.interceptor;


import com.xiefuzhong.community.entity.LoginTicket;
import com.xiefuzhong.community.entity.User;
import com.xiefuzhong.community.service.UserService;
import com.xiefuzhong.community.util.CookieUtil;
import com.xiefuzhong.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    //介绍：主要是控制显示登录信息，以及页面头部的展示

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    //在controller调用之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            // 查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                // 根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                // 在本次请求中持有用户:需要考虑多线程环境下使用，数据隔离
                hostHolder.setUser(user);
            }
        }

        return true;
    }

    //在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    //整个请求完成之后，也就是DispatcherServlet渲染了视图后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
