package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.IntoAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IntoAssessmentDao {

    /**
     * 新增一条转入评估表记录
     * @param intoAssessment
     * @return boolean
     */
    boolean insert(IntoAssessment intoAssessment);

    /**
     * 根据主键id查询一条转入评估表记录
     * @param id
     * @return IntoAssessment
     */
    IntoAssessment queryData(String id);

    /**
     * 更新一条转入评估表记录
     * @param intoAssessment
     * @return boolean
     */
    boolean update(IntoAssessment intoAssessment);

    /**
     * 根据条件分页查询转入评估表记录列表
     * @param map
     * @return list
     */
    List<IntoAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
