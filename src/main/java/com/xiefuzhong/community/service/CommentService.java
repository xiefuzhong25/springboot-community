package com.xiefuzhong.community.service;



import com.xiefuzhong.community.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public interface CommentService  {


    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit) ;

    public int findCommentCount(int entityType, int entityId) ;

}
