package com.jyxd.web.dao.patient;

import com.jyxd.web.data.patient.PatientScoreItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatientScoreItemDao {

    /**
     * 新增一条病人评分明细表记录
     * @param patientScoreItem
     * @return boolean
     */
    boolean insert(PatientScoreItem patientScoreItem);

    /**
     * 根据主键id查询一条病人评分明细表记录
     * @param id
     * @return PatientScoreFactor
     */
    PatientScoreItem queryData(String id);

    /**
     * 更新一条病人评分明细表记录
     * @param patientScoreItem
     * @return boolean
     */
    boolean update(PatientScoreItem patientScoreItem);

    /**
     * 根据条件分页查询病人评分明细表记录列表
     * @param map
     * @return list
     */
    List<PatientScoreItem> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
