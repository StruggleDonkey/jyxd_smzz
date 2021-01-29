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

    /**
     * 从his系统视图查询所有病人转移信息
     * @return
     */
    List<Map<String, Object>> getTransferByHis(Map<String,Object> map);

    /**
     * 从his系统视图查询所有手术信息
     * @return
     */
    List<Map<String, Object>> getOperationByHis(Map<String,Object> map);

    /**
     * 从his系统视图查询所有医嘱执行信息
     * @return
     */
    List<Map<String, Object>> getMedOrderExecByHis(Map<String,Object> map);

    /**
     * 从his系统视图查询用药分类字典
     * @return
     */
    List<Map<String, Object>> getDrugTypeByHis(Map<String,Object> map);

    /**
     * 从his系统视图查询补液类型字典
     * @return
     */
    List<Map<String, Object>> getOrderAttrByHis(Map<String,Object> map);

    /**
     * 从his系统视图查询 给药途径字典
     * @return
     */
    List<Map<String, Object>> getUseModeByHis(Map<String,Object> map);

    /**
     * 从his系统视图查询 病情
     * @return
     */
    List<Map<String, Object>> getIllnessStateByHis(Map<String,Object> map);

    /**
     * 从his系统视图查询 护理级别
     * @return
     */
    List<Map<String, Object>> getNursingLevelByHis(Map<String,Object> map);

    /**
     * 从his系统视图查询 婚姻状况
     * @return
     */
    List<Map<String, Object>> getMaritalStateByHis(Map<String,Object> map);
}
