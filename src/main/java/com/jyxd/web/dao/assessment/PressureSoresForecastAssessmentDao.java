package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.PressureSoresForecastAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PressureSoresForecastAssessmentDao {

    /**
     * 新增一条压疮预报表记录
     * @param pressureSoresForecastAssessment
     * @return boolean
     */
    boolean insert(PressureSoresForecastAssessment pressureSoresForecastAssessment);

    /**
     * 根据主键id查询一条压疮预报表记录
     * @param id
     * @return PressureSoresForecastAssessment
     */
    PressureSoresForecastAssessment queryData(String id);

    /**
     * 更新一条压疮预报表记录
     * @param pressureSoresForecastAssessment
     * @return boolean
     */
    boolean update(PressureSoresForecastAssessment pressureSoresForecastAssessment);

    /**
     * 根据条件分页查询压疮预报表记录列表
     * @param map
     * @return list
     */
    List<PressureSoresForecastAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
