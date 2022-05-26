package com.xiefuzhong.community.service.impl;

import com.xiefuzhong.community.dao.DiscussPostMapper;
import com.xiefuzhong.community.entity.DiscussPost;
import com.xiefuzhong.community.service.DiscussPostService;
import com.xiefuzhong.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * 查询贴子信息，进行页面贴子的展示
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    /**
     * 查询有关贴子的行数
     * @param userId
     * @return
     */
    @Override
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    @Override
    public int addDiscussPost(DiscussPost post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 转义HTML标记:把标签当成普通文本
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    @Override
    public DiscussPost findDiscussPostId(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }


}
