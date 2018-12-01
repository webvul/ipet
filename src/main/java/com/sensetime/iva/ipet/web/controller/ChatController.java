package com.sensetime.iva.ipet.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sensetime.iva.ipet.common.Code;
import com.sensetime.iva.ipet.entity.AreaEntity;
import com.sensetime.iva.ipet.util.ReturnUtil;
import com.sensetime.iva.ipet.vo.response.ResponseBody;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author : ChaiLongLong
 * @date : 2018/8/16 17:50
 */
@RestController
public class ChatController {

    @Autowired
    SimpMessagingTemplate template;

    @GetMapping(value = { "/chat" },produces="application/json;charset=UTF-8")
    @ApiOperation(value="chat", notes="chat")
    public ResponseBody chat(){
        try {
            template.convertAndSendToUser("admin","/message","test");
            return ReturnUtil.success("chat成功");
        }catch (Exception e){
            return ReturnUtil.error(Code.ERROR,"chat失败");
        }

    }




}
