package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.NursingAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NursingAssessmentDao {

    /**
     * 新增一条护理评估表记录
     * @param nursingAssessment
     * @return boolean
     */
    boolean insert(NursingAssessment nursingAssessment);

    /**
     * 根据主键id查询一条护理评估表记录
     * @param id
     * @return NursingAssessment
     */
    NursingAssessment queryData(String id);

    /**
     * 更新一条护理评估表记录
     * @param nursingAssessment
     * @return boolean
     */
    boolean update(NursingAssessment nursingAssessment);

    /**
     * 根据条件分页查询护理评估表记录列表
     * @param map
     * @return list
     */
    List<NursingAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
