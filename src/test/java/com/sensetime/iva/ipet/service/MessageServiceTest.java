package com.sensetime.iva.ipet.service;

import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.Message;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/16 19:44
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageServiceTest extends AbstractServiceTxTest{

    @Autowired
    MessageService messageService;
    @Autowired
    AccountService accountService;

    private static int receiveId;
    private static int id;

    @Test
    public void test0Init(){
        AccountEntity accountEntity = accountService.loadAccountByUsername("admin");
        receiveId = accountEntity.getId();
    }

    @Test
    public void test1AddMessage(){
        Message message = new Message();
        message.setContent("测试消息内容");
        message.setSend("系统");
        message.setHandle(false);
        message.setReceiverId(receiveId);
        message.setTitle(IConstant.PROJECT_CONCLUSION_REPLY_TITLE);
        message.setEvent(IConstant.PROJECT_CONCLUSION_REPLY);
        message.setParam1(1);
        messageService.addMessage(message);
        id = message.getId();
        Assert.assertTrue(id != 0);
    }

    @Test
    public void test2GetUnReadByRecId(){
        List<Message> messages = messageService.getUnReadByRecId(receiveId);
        Assert.assertTrue(messages.size() > 0);
    }

    @Test
    public void test3UpdateUnHandleByEventAndParam1(){
        messageService.updateUnHandleByEventAndParam1(IConstant.PROJECT_CONCLUSION_REPLY, 1, new Timestamp(System.currentTimeMillis()));
        Message message = messageService.queryById(id);
        Assert.assertTrue(message.isHandle());
    }

    @Test
    public void test4UpdateMessageRead(){
        Message message = messageService.queryById(id);
        message.setHandle(false);
        message.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        messageService.updateMessageRead(message);

        Message updateResult = messageService.queryById(id);
        Assert.assertTrue(!updateResult.isHandle());
    }

    @Test
    public void test5DeleteById(){
        messageService.deleteById(id);
        Assert.assertNull(messageService.queryById(id));
    }

}
