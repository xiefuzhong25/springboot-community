package com.xiefuzhong.community.entity;

import java.util.List;

/**
 * @Description:
 * @Author: xiefuzhong
 * @CreateTime: 2022/6/8 0008 10:28
 *  自定义实体
 *  用于暂存es中查询到的列表和总行数
 */

public class SearchResult {
    private List<DiscussPost> list;
    private long total;
    public SearchResult(List<DiscussPost> list, long total) {
        this.list = list;
        this.total = total;
    }

    public List<DiscussPost> getList() {
        return list;
    }

    public void setList(List<DiscussPost> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
