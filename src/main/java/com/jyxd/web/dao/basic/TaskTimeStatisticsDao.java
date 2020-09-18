package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.TaskTimeStatistics;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TaskTimeStatisticsDao {

    /**
     * 新增一条统计任务执行时间表记录
     * @param taskTimeStatistics
     * @return boolean
     */
    boolean insert(TaskTimeStatistics taskTimeStatistics);

    /**
     * 根据主键id查询一条统计任务执行时间表记录
     * @param id
     * @return TaskTimeStatistics
     */
    TaskTimeStatistics queryData(String id);

    /**
     * 更新一条统计任务执行时间表记录
     * @param taskTimeStatistics
     * @return boolean
     */
    boolean update(TaskTimeStatistics taskTimeStatistics);

    /**
     * 根据条件分页查询统计任务执行时间表记录列表
     * @param map
     * @return list
     */
    List<TaskTimeStatistics> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
