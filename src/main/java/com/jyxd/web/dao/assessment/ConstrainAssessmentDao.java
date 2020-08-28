package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.ConstrainAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ConstrainAssessmentDao {

    /**
     * 新增一条保护性约束知情同意书表记录
     * @param constrainAssessment
     * @return boolean
     */
    boolean insert(ConstrainAssessment constrainAssessment);

    /**
     * 根据主键id查询一条保护性约束知情同意书表记录
     * @param id
     * @return ConstrainAssessment
     */
    ConstrainAssessment queryData(String id);

    /**
     * 更新一条保护性约束知情同意书表记录
     * @param constrainAssessment
     * @return boolean
     */
    boolean update(ConstrainAssessment constrainAssessment);

    /**
     * 根据条件分页查询保护性约束知情同意书表记录列表
     * @param map
     * @return list
     */
    List<ConstrainAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
