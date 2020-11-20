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

    /**
     * 根据评分类型 分数 病人主键id 查询评分记录列表
     * @param map
     * @return
     */
    List<PatientScore> queryDataByScoreAndType(Map<String,Object> map);

    /**
     * 根据评分类型 病人主键id 查询评分记录列表 （group by 评分时间）
     * @param map
     * @return
     */
    List<PatientScore> queryDataListGroupByTime(Map<String,Object> map);

    /**
     * 根据评分时间 评分类型 病人主键id 查询 病人评分列表
     * @param map
     * @return
     */
    List<Map<String,Object>> getPatientScoreList(Map<String,Object> map);

    /**
     * 根据主键id 获取评分时间  带准确时分秒的
     * @param id
     * @return
     */
     String getTimeById(String id);

    /**
     * 重症评分--跌倒坠床--复制评分--查询病人该项评分列表
     * @param map patientId type
     * @return
     */
     List<Map<String,Object>>  getListByPatientIdAndType(Map<String,Object> map);
}
