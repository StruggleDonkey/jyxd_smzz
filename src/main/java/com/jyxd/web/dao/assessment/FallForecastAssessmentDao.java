package com.jyxd.web.dao.assessment;

import com.jyxd.web.data.assessment.FallForecastAssessment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FallForecastAssessmentDao {

    /**
     * 新增一条跌倒预报表记录
     * @param fallForecastAssessment
     * @return boolean
     */
    boolean insert(FallForecastAssessment fallForecastAssessment);

    /**
     * 根据主键id查询一条跌倒预报表记录
     * @param id
     * @return FallForecastAssessment
     */
    FallForecastAssessment queryData(String id);

    /**
     * 更新一条跌倒预报表记录
     * @param fallForecastAssessment
     * @return boolean
     */
    boolean update(FallForecastAssessment fallForecastAssessment);

    /**
     * 根据条件分页查询跌倒预报表记录列表
     * @param map
     * @return list
     */
    List<FallForecastAssessment> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
