package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BloodPurify;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BloodPurifyDao {

    /**
     * 新增一条血液净化基本信息表记录
     * @param bloodPurify
     * @return boolean
     */
    boolean insert(BloodPurify bloodPurify);

    /**
     * 根据主键id查询一条血液净化基本信息表记录
     * @param id
     * @return BloodPurify
     */
    BloodPurify queryData(String id);

    /**
     * 更新一条血液净化基本信息表记录
     * @param bloodPurify
     * @return boolean
     */
    boolean update(BloodPurify bloodPurify);

    /**
     * 根据条件分页查询血液净化基本信息表记录列表
     * @param map
     * @return list
     */
    List<BloodPurify> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询血液净化基本信息表记录列表
     * @param map
     * @return list
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int getNum(Map<String, Object> map);

    /**
     * 根据maintenanceId查询对象列表
     * @param maintenanceId
     * @return
     */
     List<BloodPurify> queryListByMaintenanceId(String maintenanceId);

    /**
     * 根据根据maintenanceId查询对象
     * @param map
     * @return
     */
    Map<String,Object> getDataByMaintenanceId(Map<String, Object> map);

}
