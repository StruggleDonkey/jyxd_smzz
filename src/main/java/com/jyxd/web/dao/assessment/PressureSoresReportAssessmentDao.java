package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.PressureSoresReportAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PressureSoresReportAssessmentDao {

    /**
     * 新增一条压疮报告表记录
     * @param pressureSoresReportAssessment
     * @return boolean
     */
    boolean insert(PressureSoresReportAssessment pressureSoresReportAssessment);

    /**
     * 根据主键id查询一条压疮报告表记录
     * @param id
     * @return PressureSoresReportAssessment
     */
    PressureSoresReportAssessment queryData(String id);

    /**
     * 更新一条压疮报告表记录
     * @param pressureSoresReportAssessment
     * @return boolean
     */
    boolean update(PressureSoresReportAssessment pressureSoresReportAssessment);

    /**
     * 根据条件分页查询压疮报告表记录列表
     * @param map
     * @return list
     */
    List<PressureSoresReportAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
