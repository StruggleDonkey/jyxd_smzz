package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.TransfusionAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TransfusionAssessmentDao {

    /**
     * 新增一条输血安全护理记录单记录
     * @param transfusionAssessment
     * @return boolean
     */
    boolean insert(TransfusionAssessment transfusionAssessment);

    /**
     * 根据主键id查询一条输血安全护理记录单记录
     * @param id
     * @return TransfusionAssessment
     */
    TransfusionAssessment queryData(String id);

    /**
     * 更新一条输血安全护理记录单记录
     * @param transfusionAssessment
     * @return boolean
     */
    boolean update(TransfusionAssessment transfusionAssessment);

    /**
     * 根据条件分页查询输血安全护理记录单记录列表
     * @param map
     * @return list
     */
    List<TransfusionAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
