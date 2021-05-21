package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.MonitorData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MonitorDataDao {

    /**
     * 新增一条监护仪数据采集表记录
     * @param monitorData
     * @return boolean
     */
    boolean insert(MonitorData monitorData);

    /**
     * 根据主键id查询一条监护仪数据采集表记录
     * @param id
     * @return MonitorData
     */
    MonitorData queryData(String id);

    /**
     * 更新一条监护仪数据采集表记录
     * @param monitorData
     * @return boolean
     */
    boolean update(MonitorData monitorData);

    /**
     * 根据条件分页查询监护仪数据采集表记录列表
     * @param map
     * @return list
     */
    List<MonitorData> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
