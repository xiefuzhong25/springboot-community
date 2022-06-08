package com.xiefuzhong.community.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiefuzhong.community.dao.elasticsearch.DiscussPostRepository;
import com.xiefuzhong.community.entity.DiscussPost;
import com.xiefuzhong.community.entity.SearchResult;
import com.xiefuzhong.community.service.ElasticSearchService;
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
 *  es的业务层
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    @Qualifier("client")
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void saveDiscussPost(DiscussPost post){
        discussPostRepository.save(post);
    }

    @Override
    public void deleteDiscussPost(int id){
        discussPostRepository.deleteById(id);
    }

    @Override
    public SearchResult searchDiscussPost(String keyword, int current, int limit) throws IOException {
        SearchRequest searchRequest = new SearchRequest("discusspost");//discusspost是索引名，就是表名
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.field("content");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery(keyword, "title", "content"))
                .sort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .sort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .from(current)// 指定从哪条开始查询，注意0开始
                .size(limit)// 需要查出的总记录条数
                .highlighter(highlightBuilder);//高亮

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<DiscussPost> list = new ArrayList<>();
        //获取数据真实总条数
        long total = searchResponse.getHits().getTotalHits().value;
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            //查询到的贴子数据构建为贴子实体
            DiscussPost discussPost = JSONObject.parseObject(hit.getSourceAsString(), DiscussPost.class);

            // 处理高亮显示的结果
            HighlightField titleField = hit.getHighlightFields().get("title");
            if (titleField != null) {
                discussPost.setTitle(titleField.getFragments()[0].toString());
            }
            HighlightField contentField = hit.getHighlightFields().get("content");
            if (contentField != null) {
                discussPost.setContent(contentField.getFragments()[0].toString());
            }
//                System.out.println(discussPost);
            list.add(discussPost);
        }
        return new SearchResult(list, total);
    }
}
