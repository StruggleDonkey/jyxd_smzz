package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.MessageDao;
import com.jyxd.web.data.basic.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public boolean insert(Message message){
        return messageDao.insert(message);
    }

    public boolean update(Message message){
        return messageDao.update(message);
    }

    public Message queryData(String id){
        return messageDao.queryData(id);
    }

    public List<Message> queryList(Map<String,Object> map){
        return messageDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return messageDao.queryNum(map);}
}
