package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.QuartzTask;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuartzTaskDao {

    /**
     * 新增一条定时任务表记录
     * @param quartzTask
     * @return boolean
     */
    boolean insert(QuartzTask quartzTask);

    /**
     * 根据主键id查询一条定时任务表记录
     * @param id
     * @return QuartzTask
     */
    QuartzTask queryData(String id);

    /**
     * 更新一条定时任务表记录
     * @param quartzTask
     * @return boolean
     */
    boolean update(QuartzTask quartzTask);

    /**
     * 根据条件分页查询定时任务表记录列表
     * @param map
     * @return list
     */
    List<QuartzTask> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据jobName 和 jobGroup 查询是否有任务
     * @param map
     * @return
     */
    QuartzTask queryDataByNameAndGroup(Map<String, Object> map);

    /**
     * 系统设置--任务管理--查询任务列表
     * @param map
     * @return
     */
    List<Map<String,Object>> getList(Map<String, Object> map);
}
