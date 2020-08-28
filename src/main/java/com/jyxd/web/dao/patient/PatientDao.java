package com.jyxd.web.dao.patient;

import com.jyxd.web.data.patient.Patient;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface PatientDao {

    /**
     * 新增一条病人表记录
     * @param patient
     * @return boolean
     */
    boolean insert(Patient patient);

    /**
     * 根据主键id查询一条病人表记录
     * @param id
     * @return Patient
     */
    Patient queryData(String id);

    /**
     * 更新一条病人表记录
     * @param patient
     * @return boolean
     */
    boolean update(Patient patient);

    /**
     * 根据条件分页查询病人表记录列表
     * @param map
     * @return list
     */
    List<Patient> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询病人表记录列表--多表查询
     * @param map
     * @return list
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数--多表查询
     * @param map
     * @return
     */
    int getNum(Map<String, Object> map);

    /**
     * 查询所有病人数量
     * @return
     */
    int getAllNum();

    /**
     * 根据条件查询需要下载的病人信息列表
     * @param map
     * @return list
     */
    List<LinkedHashMap<String, Object>> getDownloadList(Map<String, Object> map);

    /**
     * 首页查询患者现有数量 今日转入数量 今日转出数量
     * @param map
     * @return
     */
    int getNowPatientNum(Map<String, Object> map);

    /**
     *首页查询床位列表
     * @return
     */
    List<Map<String, Object>> getBedPatientList();
}
