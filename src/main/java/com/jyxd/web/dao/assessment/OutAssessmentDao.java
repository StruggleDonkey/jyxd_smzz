package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.OutAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OutAssessmentDao {

    /**
     * 新增一条出院评估表记录
     * @param outAssessment
     * @return boolean
     */
    boolean insert(OutAssessment outAssessment);

    /**
     * 根据主键id查询一条出院评估表记录
     * @param id
     * @return OutAssessment
     */
    OutAssessment queryData(String id);

    /**
     * 更新一条出院评估表记录
     * @param outAssessment
     * @return boolean
     */
    boolean update(OutAssessment outAssessment);

    /**
     * 根据条件分页查询出院评估表记录列表
     * @param map
     * @return list
     */
    List<OutAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
