package com.jyxd.web.dao.patient;

import com.jyxd.web.data.patient.Patient;
import org.springframework.stereotype.Repository;

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
}
