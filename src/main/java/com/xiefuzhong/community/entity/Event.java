package com.xiefuzhong.community.entity;

import java.util.HashMap;
import java.util.Map;

public class Event {

    private String topic; //事件主题
    private int userId; //事件的发起人
    private int entityType;//实体的类型：评论，贴子
    private int entityId;
    private int entityUserId; //实体的作者
    private Map<String, Object> data = new HashMap<>();

    public String getTopic() {
        return topic;
    }

    //set方法都将void 改Event 加一个返回值,调用就可以：xxx.setTopic(x).setUserId(XXX).setUserId(XXX)
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    //特殊处理
    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
