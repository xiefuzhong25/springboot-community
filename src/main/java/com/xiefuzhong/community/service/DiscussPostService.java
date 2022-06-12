package com.xiefuzhong.community.service;

import com.xiefuzhong.community.dao.DiscussPostMapper;
import com.xiefuzhong.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscussPostService {

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit,int orderModel);

    public int findDiscussPostRows(int userId) ;

    public int addDiscussPost(DiscussPost post);

    public  DiscussPost  findDiscussPostId(int id);

    public int updateCommentCount(int id, int commentCount);

    public int updateType(int id, int type) ;

    public int updateStatus(int id, int status) ;

    public int updateScore(int id, double score) ;

}
