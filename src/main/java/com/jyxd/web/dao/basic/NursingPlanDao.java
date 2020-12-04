package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.NursingPlan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NursingPlanDao {

    /**
     * 新增一条重症护理计划表记录
     * @param nursingPlan
     * @return boolean
     */
    boolean insert(NursingPlan nursingPlan);

    /**
     * 根据主键id查询一条重症护理计划表记录
     * @param id
     * @return NursingPlan
     */
    NursingPlan queryData(String id);

    /**
     * 更新一条重症护理计划表记录
     * @param nursingPlan
     * @return boolean
     */
    boolean update(NursingPlan nursingPlan);

    /**
     * 根据条件分页查询重症护理计划表记录列表
     * @param map
     * @return list
     */
    List<NursingPlan> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询重症护理计划表记录列表
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
     * 根据 assessmentId 查询重症护理计划表记录列表
     * @param assessmentId
     * @return list
     */
    List<NursingPlan> queryListByAssessmentId(String assessmentId);

    /**
     * 根据 assessmentId 查询重症护理计划对象
     * @param map
     * @return
     */
    Map<String, Object> queryDataByAssessmentId(Map<String, Object> map);

}
