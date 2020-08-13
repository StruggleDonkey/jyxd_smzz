package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.HospitalizedAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HospitalizedAssessmentDao {

    /**
     * 新增一条入院护理评估单记录
     * @param hospitalizedAssessment
     * @return boolean
     */
    boolean insert(HospitalizedAssessment hospitalizedAssessment);

    /**
     * 根据主键id查询一条入院护理评估单记录
     * @param id
     * @return HospitalizedAssessment
     */
    HospitalizedAssessment queryData(String id);

    /**
     * 更新一条入院护理评估单记录
     * @param hospitalizedAssessment
     * @return boolean
     */
    boolean update(HospitalizedAssessment hospitalizedAssessment);

    /**
     * 根据条件分页查询入院护理评估单记录列表
     * @param map
     * @return list
     */
    List<HospitalizedAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
