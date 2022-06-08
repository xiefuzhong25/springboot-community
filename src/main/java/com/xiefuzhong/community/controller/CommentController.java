package com.xiefuzhong.community.controller;


import com.xiefuzhong.community.entity.Comment;
import com.xiefuzhong.community.entity.DiscussPost;
import com.xiefuzhong.community.entity.Event;
import com.xiefuzhong.community.event.EventProducer;
import com.xiefuzhong.community.service.CommentService;
import com.xiefuzhong.community.service.DiscussPostService;
import com.xiefuzhong.community.util.CommunityConstant;
import com.xiefuzhong.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService discussPostService;

    //添加评论
    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment) {
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        //触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityId(comment.getEntityId())
                .setEntityType(comment.getEntityType())
                .setData("postId",discussPostId);
        //针对不同的类型，它的作者我们需要处理一下
        if (comment.getEntityType() == ENTITY_TYPE_POST){
            //当前评论的是个贴子
            DiscussPost target = discussPostService.findDiscussPostId(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }else if (comment.getEntityType() == ENTITY_TYPE_COMMENT){
            //当前评论的是一个评论
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        //发布消息：把event丢到队列中去
        eventProducer.fireEvent(event);

        //判断是贴子才触发 发帖事件
        if (comment.getEntityType() == ENTITY_TYPE_POST){
            //触发发帖事件：把新发布的贴子存到es服务器里
             event = new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);
            eventProducer.fireEvent(event);
        }


        return "redirect:/discuss/detail/" + discussPostId;
    }

}
