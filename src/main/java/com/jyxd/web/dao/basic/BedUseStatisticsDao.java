package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BedUseStatistics;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BedUseStatisticsDao {

    /**
     * 新增一条床位统计表记录
     * @param bedUseStatistics
     * @return boolean
     */
    boolean insert(BedUseStatistics bedUseStatistics);

    /**
     * 根据主键id查询一条床位统计表记录
     * @param id
     * @return BedUseStatistics
     */
    BedUseStatistics queryData(String id);

    /**
     * 更新一条床位统计表记录
     * @param bedUseStatistics
     * @return boolean
     */
    boolean update(BedUseStatistics bedUseStatistics);

    /**
     * 根据条件分页查询床位统计表记录列表
     * @param map
     * @return list
     */
    List<BedUseStatistics> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
