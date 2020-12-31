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

    /**
     * 根据评估主键查询对象列表
     * @param map
     * @return
     */
     List<TransferAssessment> getListByAssessmentIdAndPatientId(Map<String,Object> map);

    /**
     * 根据记录时间查询对象列表
     * @param map
     * @return
     */
     List<TransferAssessment> getListByDataTimeAndPatientId(Map<String,Object> map);

    /**
     * 根据条件分页查询转科交接表记录列表--多表
     * @param map
     * @return list
     */
    List<Map<String,Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数--多表
     * @param map
     * @return
     */
    int getNum(Map<String, Object> map);

    /**
     * 护理评估--入院护理评估单--历史列表--选择
     * @param map
     * @return
     */
     Map<String,Object> getDataByAssessmentId(Map<String,Object> map);
}
