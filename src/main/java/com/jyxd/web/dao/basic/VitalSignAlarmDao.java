package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.VitalSignAlarm;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VitalSignAlarmDao {

    /**
     * 新增一条生命体征告警设置记录
     * @param vitalSignAlarm
     * @return boolean
     */
    boolean insert(VitalSignAlarm vitalSignAlarm);

    /**
     * 根据主键id查询一条生命体征告警设置记录
     * @param id
     * @return VitalSignAlarm
     */
    VitalSignAlarm queryData(String id);

    /**
     * 更新一条生命体征告警设置记录
     * @param vitalSignAlarm
     * @return boolean
     */
    boolean update(VitalSignAlarm vitalSignAlarm);

    /**
     * 根据条件分页查询生命体征告警设置记录列表
     * @param map
     * @return list
     */
    List<VitalSignAlarm> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 系统设置--报警设置--查询指标名称
     * @param map
     * @return
     */
    VitalSignAlarm queryDataByType(Map<String, Object> map);
}
