package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.InOutIcuStatistics;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InOutIcuStatisticsDao {

    /**
     * 新增一条出入科统计表记录
     * @param inOutIcuStatistics
     * @return boolean
     */
    boolean insert(InOutIcuStatistics inOutIcuStatistics);

    /**
     * 根据主键id查询一条出入科统计表记录
     * @param id
     * @return InOutIcuStatistics
     */
    InOutIcuStatistics queryData(String id);

    /**
     * 更新一条出入科统计表记录
     * @param inOutIcuStatistics
     * @return boolean
     */
    boolean update(InOutIcuStatistics inOutIcuStatistics);

    /**
     * 根据条件分页查询出入科统计表记录列表
     * @param map
     * @return list
     */
    List<InOutIcuStatistics> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 统计分析--出入科--列表展示统计（按天）
     * @param map
     * @return
     */
    List<Map<String, Object>> getListByDay(Map<String, Object> map);

    /**
     * 统计分析--出入科--列表展示统计总量（按天）
     * @param map
     * @return
     */
    List<Map<String, Object>> getTotalByDay(Map<String, Object> map);

    /**
     * 统计分析--出入科--列表展示统计（按月）
     * @param map
     * @return
     */
    List<Map<String, Object>> getListByMonth(Map<String, Object> map);

    /**
     * 统计分析--出入科--列表展示统计总量（按月）
     * @param map
     * @return
     */
    List<Map<String, Object>> getTotalByMonth(Map<String, Object> map);

    /**
     * 根据条件查询出入科对象
     * @param map
     * @return
     */
    List<InOutIcuStatistics> queryDataByDate(Map<String, Object> map);
}
