package com.sensetime.iva.ipet.service.impl;

import com.sensetime.iva.ipet.entity.Message;
import com.sensetime.iva.ipet.mapper.MessageMapper;
import com.sensetime.iva.ipet.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.loading.PrivateClassLoader;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/16 19:27
 */
@Component
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageMapper messageMapper;

    @Override
    public void addMessage(Message message) {
        logger.info("addMessage param "+message.toString());
        messageMapper.addMessage(message);
    }

    @Override
    public List<Message> getUnReadByRecId(int id) {
        logger.info("getUnReadByRecId param id "+id);
        return messageMapper.getUnReadByRecId(id);
    }

    @Override
    public void updateMessageRead(Message message) {
        logger.info("updateMessageRead param  "+message.toString());
        messageMapper.updateMessageRead(message);
    }

    @Override
    public void deleteById(int id) {
        logger.info("deleteById param  id "+id);
        messageMapper.deleteById(id);
    }

    @Override
    public Message queryById(int id) {
        logger.info("queryById param  id "+id);
        return messageMapper.queryById(id);
    }

    @Override
    public void updateUnHandleByEventAndParam1(int event, int param1, Timestamp updateTime) {
        logger.info("updateUnHandleByEventAndParam1 param  event "+event+" param1 "+param1);
        messageMapper.updateUnHandleByEventAndParam1(event, param1, updateTime);
    }

    @Override
    public List<Message> selectAll() {
        logger.info("selectAll Messages");
        return messageMapper.selectAll();
    }
}
