package com.jyxd.web.dao.patient;

import com.jyxd.web.data.patient.PatientScoreFactor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatientScoreFactorDao {

    /**
     * 新增一条病人评分评估条件表记录
     * @param patientScoreFactor
     * @return boolean
     */
    boolean insert(PatientScoreFactor patientScoreFactor);

    /**
     * 根据主键id查询一条病人评分评估条件表记录
     * @param id
     * @return PatientScoreFactor
     */
    PatientScoreFactor queryData(String id);

    /**
     * 更新一条病人评分评估条件表记录
     * @param patientScoreFactor
     * @return boolean
     */
    boolean update(PatientScoreFactor patientScoreFactor);

    /**
     * 根据条件分页查询病人评分评估条件表记录列表
     * @param map
     * @return list
     */
    List<PatientScoreFactor> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
