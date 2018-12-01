package com.sensetime.iva.ipet.web.controller;

import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.common.IConstant;
import com.sensetime.iva.ipet.common.PredefineResource;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.entity.Message;
import com.sensetime.iva.ipet.entity.enumeration.RequestMethod;
import com.sensetime.iva.ipet.entity.enumeration.ResourceType;
import com.sensetime.iva.ipet.service.MessageService;
import com.sensetime.iva.ipet.util.AccountUtils;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import com.sensetime.iva.ipet.web.annotation.MyResources;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/17 9:53
 */
@Api(description  = "消息（获取未处理消息、处理消息）")
@RestController
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @MyResources(name ="获取未处理消息",code="get_untreated_message",type=ResourceType.URL,path="/message",parent = PredefineResource.MESSAGE_NODE_CODE,seq = 1,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/message" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="message", notes="获取未处理消息")
    public ResponseBody<List<Message>> getUntreatedMessages(){
        AccountEntity accountEntity = AccountUtils.getCurrentHr();
        if(accountEntity == null){
            logger.error("getUnHandleMessages AccountUtils.getCurrentHr() null");
            return ReturnUtil.error(Code.ERROR,"系统内部异常");
        }
        logger.info("getUnHandleMessages id "+accountEntity.getId());
        List<Message> messages = messageService.getUnReadByRecId(accountEntity.getId());
        return ReturnUtil.success("获取未处理消息成功",messages);
    }

    @MyResources(name ="处理消息",code="set_message_handle",type=ResourceType.URL,path="/message/{id}",parent = PredefineResource.MESSAGE_NODE_CODE,seq = 2,method = RequestMethod.GET,role = IConstant.ROLE_PM+","+IConstant.ROLE_MANAGER)
    @GetMapping(value = { "/message/{id}" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="处理消息", notes="设置消息已处理")
    @ApiImplicitParam(name = "id", value = "消息id", dataType = "Integer", paramType = "path")
    public ResponseBody setMessageHandled(@PathVariable("id") Integer id){
        logger.info("setMessageHandled param  id "+ id);
        Message message = messageService.queryById(id);
        if(message == null || message.isHandle()){
            logger.error("setMessageHandled message 消息不存在或已处理");
            return ReturnUtil.error(Code.ERROR,"消息不存在或已处理");
        }
        message.setHandle(true);
        messageService.updateMessageRead(message);
        return ReturnUtil.success("处理消息成功",null);
    }


}
