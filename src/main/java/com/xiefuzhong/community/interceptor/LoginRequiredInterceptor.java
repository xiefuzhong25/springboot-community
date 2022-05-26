package com.xiefuzhong.community.interceptor;


import com.xiefuzhong.community.annotation.LoginRequired;
import com.xiefuzhong.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //介绍： 如果某个方法加了@LoginRequired 注解就会被拦截到 ，拦截到之后验证是否登录了，没登录直接重定向到login
        if (handler instanceof HandlerMethod) {
            //  将object转成方法类型
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //按照指定的类型去去注解
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            if (loginRequired != null && hostHolder.getUser() == null) {
                //有方法中使用了自定义注解，并且从该次线程中没有得到用户信息：非法请求
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}
