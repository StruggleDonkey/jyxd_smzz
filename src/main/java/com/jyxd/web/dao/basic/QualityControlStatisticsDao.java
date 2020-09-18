package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.QualityControlStatistics;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QualityControlStatisticsDao {

    /**
     * 新增一条质控统计表记录
     * @param qualityControlStatistics
     * @return boolean
     */
    boolean insert(QualityControlStatistics qualityControlStatistics);

    /**
     * 根据主键id查询一条质控统计表记录
     * @param id
     * @return QualityControlStatistics
     */
    QualityControlStatistics queryData(String id);

    /**
     * 更新一条质控统计表记录
     * @param qualityControlStatistics
     * @return boolean
     */
    boolean update(QualityControlStatistics qualityControlStatistics);

    /**
     * 根据条件分页查询质控统计表记录列表
     * @param map
     * @return list
     */
    List<QualityControlStatistics> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
