package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.Monitor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MonitorDao {

    /**
     * 新增一条监护仪抓取数据存放临时表记录
     * @param monitor
     * @return boolean
     */
    boolean insert(Monitor monitor);

    /**
     * 根据主键id查询一条监护仪抓取数据存放临时表记录
     * @param id
     * @return Monitor
     */
    Monitor queryData(String id);

    /**
     * 更新一条监护仪抓取数据存放临时表记录
     * @param monitor
     * @return boolean
     */
    boolean update(Monitor monitor);

    /**
     * 根据条件分页查询监护仪抓取数据存放临时表记录列表
     * @param map
     * @return list
     */
    List<Monitor> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
