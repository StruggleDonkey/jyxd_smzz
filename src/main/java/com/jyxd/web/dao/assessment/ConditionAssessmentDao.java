package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.ConditionAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ConditionAssessmentDao {

    /**
     * 新增一条患者病情介绍及家属沟通表记录
     * @param conditionAssessment
     * @return boolean
     */
    boolean insert(ConditionAssessment conditionAssessment);

    /**
     * 根据主键id查询一条患者病情介绍及家属沟通表记录
     * @param id
     * @return ConditionAssessment
     */
    ConditionAssessment queryData(String id);

    /**
     * 更新一条患者病情介绍及家属沟通表记录
     * @param conditionAssessment
     * @return boolean
     */
    boolean update(ConditionAssessment conditionAssessment);

    /**
     * 根据条件分页查询患者病情介绍及家属沟通表记录列表
     * @param map
     * @return list
     */
    List<ConditionAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
