package com.xiefuzhong.community.interceptor;


import com.xiefuzhong.community.entity.User;
import com.xiefuzhong.community.service.MessageService;
import com.xiefuzhong.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该拦截器用于头部中显示未读消息的数量
 */
@Component
public class MessageInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageService messageService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            //私信的数量
            int letterUnreadCount = messageService.findLetterUnreadCount(user.getId(), null);
            //系统通知的数量
            int noticeUnreadCount = messageService.findNoticeUnreadCount(user.getId(), null);
            //两个加起来得到头部消息总未读数量
            modelAndView.addObject("allUnreadCount", letterUnreadCount + noticeUnreadCount);
        }
    }
}
