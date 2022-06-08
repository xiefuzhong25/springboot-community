package com.xiefuzhong.community.dao.elasticsearch;

import com.xiefuzhong.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/6/7 0007 21:30
 *      DiscussPost:实体类型
 *      Integer：主键类型
 */
@Repository
public interface  DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {
}
