package com.sensetime.iva.ipet.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.Message;
import com.sensetime.iva.ipet.service.AccountService;
import com.sensetime.iva.ipet.service.MessageService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/17 16:09
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageControllerTest extends AbstractRestTxController {

    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;


    /**
     * 初始化SecurityContextHolder参数，用于使用AccountUtils.getCurrentHr()功能模块的测试初始化数据
     */
    public void initSecurityContextDate(){
        //根据用户名username加载userDetails
        UserDetails userDetails = accountService.loadAccountByUsername("admin");

        //根据userDetails构建新的Authentication,这里使用了
        //PreAuthenticatedAuthenticationToken当然可以用其他token,如UsernamePasswordAuthenticationToken
        PreAuthenticatedAuthenticationToken authentication =
                new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(),userDetails.getAuthorities());

        //设置authentication中details
//            authentication.setDetails(new WebAuthenticationDetails(request));

        //存放authentication到SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
//            HttpSession session = request.getSession(true);
        //在session中存放security context,方便同一个session中控制用户的其他操作
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

    }

    @Test
    @Transactional
    public void test1GetUnHandleMessages(){

        UserDetails userDetails = accountService.loadAccountByUsername("admin");

        Message message = new Message();
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));
        message.setHandle(false);
        message.setParam1(1);
        message.setSend("系统");
        message.setContent("消息测试内容");
        message.setTitle("测试消息title");
        message.setEvent(IConstant.PROJECT_CONCLUSION_REPLY);
        message.setReceiverId(((AccountEntity) userDetails).getId());
        messageService.addMessage(message);

        initSecurityContextDate();

        try {
            MvcResult result = getMockMvc().perform(get("/message"))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            List<Message> messages = JSON.parseArray(jsonObject.get("data").toString(), Message.class);
            Assert.assertEquals(jsonObject.get("desc"),"获取未处理消息成功");
            Assert.assertTrue(messages.size() >= 1 );

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void test2SetMessageHandled(){

        UserDetails userDetails = accountService.loadAccountByUsername("admin");

        Message message = new Message();
        message.setCreateTime(new Timestamp(System.currentTimeMillis()));
        message.setHandle(false);
        message.setParam1(1);
        message.setSend("系统");
        message.setContent("消息测试内容");
        message.setTitle("测试消息title");
        message.setEvent(IConstant.PROJECT_CONCLUSION_REPLY);
        message.setReceiverId(((AccountEntity) userDetails).getId());
        messageService.addMessage(message);

        try {
            MvcResult result = getMockMvc().perform(get("/message/"+message.getId()))
                    .andExpect(status().isOk())// 模拟向testRest发送get请求
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
                    .andReturn();
            JSONObject jsonObject = JSON.parseObject(result.getResponse().getContentAsString());
            Assert.assertEquals(jsonObject.get("desc"),"处理消息成功");
            Assert.assertTrue(messageService.queryById(message.getId()).isHandle() );
        } catch (Exception e){
            e.printStackTrace();
        }


    }



}
