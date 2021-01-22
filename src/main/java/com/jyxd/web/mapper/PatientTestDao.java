package com.jyxd.web.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatientTestDao {

    /**
     * 根据条件查询结果
     * @param map
     * @return list
     */
    List<Map<String, Object>> queryListTest(Map<String, Object> map);

    /**
     * 从his数据库视图中查询科室字典数据
     * @param map
     * @return list
     */
    List<Map<String, Object>> getDepartmentByHis(Map<String, Object> map);

    /**
     * 从his数据库视图中查询病区字典数据
     * @param map
     * @return list
     */
    List<Map<String, Object>> getWardByHis(Map<String, Object> map);

    /**
     * 从his数据库视图中查询床位字典数据
     * @param map
     * @return list
     */
    List<Map<String, Object>> getBedByHis(Map<String, Object> map);

    /**
     * 从his数据库视图中查询职工信息数据
     * @param map
     * @return list
     */
    List<Map<String, Object>> getUserByHis(Map<String, Object> map);

    /**
     * 从his系统视图查询所有病人信息
     * @return
     */
     List<Map<String, Object>> getPatientByHis(Map<String,Object> map);

}
