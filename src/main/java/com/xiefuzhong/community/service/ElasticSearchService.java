package com.xiefuzhong.community.service;

import com.alibaba.fastjson.JSONObject;
import com.xiefuzhong.community.dao.elasticsearch.DiscussPostRepository;
import com.xiefuzhong.community.entity.DiscussPost;
import com.xiefuzhong.community.entity.SearchResult;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/6/8 0008 10:21
 *  es业务接口
 */
public interface ElasticSearchService {


    //将贴子保存到es
    public void saveDiscussPost(DiscussPost post);

    //将贴子从es中删除
    public void deleteDiscussPost(int id);

    //根据标题和内容进行搜索
    public SearchResult searchDiscussPost(String keyword, int current, int limit) throws IOException;

}
