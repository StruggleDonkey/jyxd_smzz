package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.Message;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MessageDao {

    /**
     * 新增一条消息通知表记录
     * @param message
     * @return boolean
     */
    boolean insert(Message message);

    /**
     * 根据主键id查询一条消息通知表记录
     * @param id
     * @return Message
     */
    Message queryData(String id);

    /**
     * 更新一条消息通知表记录
     * @param message
     * @return boolean
     */
    boolean update(Message message);

    /**
     * 根据条件分页查询消息通知表s记录列表
     * @param map
     * @return list
     */
    List<Message> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
