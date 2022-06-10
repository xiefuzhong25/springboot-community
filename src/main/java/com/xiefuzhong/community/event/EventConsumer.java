package com.xiefuzhong.community.event;

import com.alibaba.fastjson.JSONObject;
import com.xiefuzhong.community.service.impl.ElasticSearchServiceImpl;
import com.xiefuzhong.community.entity.DiscussPost;
import com.xiefuzhong.community.entity.Event;
import com.xiefuzhong.community.entity.Message;
import com.xiefuzhong.community.service.DiscussPostService;
import com.xiefuzhong.community.service.MessageService;
import com.xiefuzhong.community.util.CommunityConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventConsumer implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private ElasticSearchServiceImpl elasticSearchService;

    //消费主题事件： 评论，点赞，关注
    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            logger.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        // 发送站内通知
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic()); //存的主题
        message.setCreateTime(new Date());

        //message通知的内容
        Map<String, Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());
        if (!event.getData().isEmpty()) {
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }
        message.setContent(JSONObject.toJSONString(content));

        messageService.addMessage(message);
    }

    //消费主题事件：发帖
    @KafkaListener(topics = {TOPIC_PUBLISH})
    public  void  handlePublishMessage(ConsumerRecord record){
        if (record == null || record.value() == null) {
            logger.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        DiscussPost post = discussPostService.findDiscussPostId(event.getEntityId());
        //存到es服务器中
        elasticSearchService.saveDiscussPost(post);


    }

    //消费主题事件：删帖
    @KafkaListener(topics = {TOPIC_DELETE})
    public  void  handleDeleteMessage(ConsumerRecord record){
        if (record == null || record.value() == null) {
            logger.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        //从es服务器中删除贴子
        elasticSearchService.deleteDiscussPost(event.getEntityId());
    }
}
