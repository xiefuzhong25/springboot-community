package com.xiefuzhong.community.service;


import com.xiefuzhong.community.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    public List<Message> findConversations(int userId, int offset, int limit) ;

    public int findConversationCount(int userId) ;

    public List<Message> findLetters(String conversationId, int offset, int limit);

    public int findLetterCount(String conversationId) ;

    public int findLetterUnreadCount(int userId, String conversationId);

    public int addMessage(Message message) ;

    public int readMessage(List<Integer> ids) ;

    public Message findLatestNotice(int userId, String topic) ;

    public int findNoticeCount(int userId, String topic);

    public int findNoticeUnreadCount(int userId, String topic);

    public List<Message> findNotices(int userId, String topic, int offset, int limit) ;

}
