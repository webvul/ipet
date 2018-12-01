package com.sensetime.iva.ipet.mapper;

import com.sensetime.iva.ipet.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : ChaiLongLong
 * @date : 2018/8/16 19:10
 */
@Repository
public interface MessageMapper {

    /**
     * 添加消息
     * @param message 消息参数
     */
    void addMessage(Message message);

    /**
     * 根据receiver_id查询未读消息
     * @param id receiver_id
     * @return 未读消息
     */
    List<Message> getUnReadByRecId(int id);

    /**
     * 设置未处理消息为已处理
     * @param message 消息参数
     */
    void updateMessageRead(Message message);

    /**
     * 根据id删除消息
     * @param id 消息id
     */
    void deleteById(int id);

    /**
     * 根据消息id查询消息
     * @param id 消息id
     * @return 消息
     */
    Message queryById(int id);

    /**
     * 根据事件 参数 设置未处理消息为已处理
     * @param event 事件
     * @param param1 参数
     * @param updateTime 处理时间
     */
    void updateUnHandleByEventAndParam1(@Param("event") int event, @Param("param1") int param1, @Param("updateTime") Timestamp updateTime);
    /**
     *全部通知
     * @return 全部数据
     */
    @Select("select * from message")
    List<Message> selectAll();
}
