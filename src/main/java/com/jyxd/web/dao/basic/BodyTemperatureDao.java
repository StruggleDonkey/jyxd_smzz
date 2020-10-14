package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BodyTemperature;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BodyTemperatureDao {

    /**
     * 新增一条体温单数据表记录
     * @param bodyTemperature
     * @return boolean
     */
    boolean insert(BodyTemperature bodyTemperature);

    /**
     * 根据主键id查询一条体温单数据表记录
     * @param id
     * @return BodyTemperature
     */
    BodyTemperature queryData(String id);

    /**
     * 更新一条体温单数据表记录
     * @param bodyTemperature
     * @return boolean
     */
    boolean update(BodyTemperature bodyTemperature);

    /**
     * 根据条件分页查询体温单数据表记录列表
     * @param map
     * @return list
     */
    List<BodyTemperature> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据时间查询在科病人的体温图数据
     * @param map
     * @return
     */
    List<Map<String,Object>> getListByTime(Map<String, Object> map);

    /**
     * 根据时间和code查询体温单对象
     * @param map
     * @return
     */
    BodyTemperature queryDataByTimeAndCode(Map<String, Object> map);
}
