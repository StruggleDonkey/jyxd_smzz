package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.TransferAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TransferAssessmentDao {

    /**
     * 新增一条转科交接表记录
     * @param transferAssessment
     * @return boolean
     */
    boolean insert(TransferAssessment transferAssessment);

    /**
     * 根据主键id查询一条转科交接表记录
     * @param id
     * @return TransferAssessment
     */
    TransferAssessment queryData(String id);

    /**
     * 更新一条转科交接表记录
     * @param transferAssessment
     * @return boolean
     */
    boolean update(TransferAssessment transferAssessment);

    /**
     * 根据条件分页查询转科交接表记录列表
     * @param map
     * @return list
     */
    List<TransferAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
