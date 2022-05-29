package com.xiefuzhong.community.service;

import com.xiefuzhong.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {



    // 点赞
    public void like(int userId, int entityType, int entityId, int entityUserId) ;

    // 查询某实体点赞的数量
    public long findEntityLikeCount(int entityType, int entityId) ;

    // 查询某人对某实体的点赞状态
    public int findEntityLikeStatus(int userId, int entityType, int entityId) ;

    // 查询某个用户获得的赞
    public int findUserLikeCount(int userId) ;

}
