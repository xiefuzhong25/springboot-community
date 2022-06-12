package com.xiefuzhong.community.controller;


import com.xiefuzhong.community.entity.DiscussPost;
import com.xiefuzhong.community.entity.Page;
import com.xiefuzhong.community.entity.User;
import com.xiefuzhong.community.service.LikeService;
import com.xiefuzhong.community.service.impl.DiscussPostServiceImpl;
import com.xiefuzhong.community.service.impl.UserServiceImpl;
import com.xiefuzhong.community.util.CommunityConstant;
import com.xiefuzhong.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private DiscussPostServiceImpl discussPostService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page, @RequestParam(name = "orderModel",defaultValue = "0") int orderModel) {
        // 方法调用钱,SpringMVC会自动实例化Model和Page,并将Page注入Model.
        // 所以,在thymeleaf中可以直接访问Page对象中的数据.
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index?orderModel=" + orderModel);

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit(),orderModel);
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                //贴子装进去
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);

                //查询主页中贴子的赞的数量
                long  likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST ,post.getId());
                map.put("likeCount",likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        model.addAttribute("orderModel", orderModel);
        return "/index";
    }

    //统一异常处理类中（控制器通知），重定向需要用到
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }

    //security进行权限验证时，权限不足的时候跳转指定页面
    @RequestMapping(path = "/denied", method = RequestMethod.GET)
    public String getDeniedPage() {
        return "/error/400";
    }
}
