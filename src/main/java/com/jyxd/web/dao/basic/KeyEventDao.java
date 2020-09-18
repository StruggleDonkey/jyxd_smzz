package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.KeyEvent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface KeyEventDao {

    /**
     * 新增一条关键事件表记录
     * @param keyEvent
     * @return boolean
     */
    boolean insert(KeyEvent keyEvent);

    /**
     * 根据主键id查询一条关键事件表记录
     * @param id
     * @return KeyEvent
     */
    KeyEvent queryData(String id);

    /**
     * 更新一条关键事件表记录
     * @param keyEvent
     * @return boolean
     */
    boolean update(KeyEvent keyEvent);

    /**
     * 根据条件分页查询关键事件表记录列表
     * @param map
     * @return list
     */
    List<KeyEvent> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
