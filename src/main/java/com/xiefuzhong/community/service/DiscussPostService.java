package com.xiefuzhong.community.service;

import com.xiefuzhong.community.dao.DiscussPostMapper;
import com.xiefuzhong.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscussPostService {

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit);

    public int findDiscussPostRows(int userId) ;

    public int addDiscussPost(DiscussPost post);

}
