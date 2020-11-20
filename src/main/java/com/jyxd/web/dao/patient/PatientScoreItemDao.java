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

    /**
     * 根据条件查询病人评分明细对象 评分时间 评分项 评分明细 病人主键id 病人评分主键id
     * @param map
     * @return
     */
    PatientScoreItem queryDataByTypeAndTime(Map<String,Object> map);

    /**
     * 根据病人评分主键id 查询病人评分明细中 评分明细主键id 集合
     * @param patientScoreId
     * @return
     */
    List<String> getItemIdByPatientScoreId(String patientScoreId);

    /**
     * 根据病人评分主键id 查询病人评分明细列表
     * @param map
     * @return
     */
    List<PatientScoreItem> queryListByPatientScoreId(Map<String,Object> map);

    /**
     * 删除对象
     * @param
     * @return
     */
     boolean deleteData(String id);
}
