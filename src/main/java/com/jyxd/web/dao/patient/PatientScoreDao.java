package com.jyxd.web.dao.patient;

import com.jyxd.web.data.patient.PatientScore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatientScoreDao {

    /**
     * 新增一条病人评分表记录
     * @param patientScore
     * @return boolean
     */
    boolean insert(PatientScore patientScore);

    /**
     * 根据主键id查询一条病人评分表记录
     * @param id
     * @return PatientScore
     */
    PatientScore queryData(String id);

    /**
     * 更新一条病人评分表记录
     * @param patientScore
     * @return boolean
     */
    boolean update(PatientScore patientScore);

    /**
     * 根据条件分页查询病人评分表记录列表
     * @param map
     * @return list
     */
    List<PatientScore> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
