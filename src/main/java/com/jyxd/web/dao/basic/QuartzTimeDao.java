package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.QuartzTime;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuartzTimeDao {

    /**
     * 新增一条定时任务上次执行的时间记录
     * @param quartzTime
     * @return boolean
     */
    boolean insert(QuartzTime quartzTime);

    /**
     * 根据主键id查询一条定时任务上次执行的时间记录
     * @param id
     * @return BasicNursing
     */
    QuartzTime queryData(String id);

    /**
     * 更新一条定时任务上次执行的时间记录
     * @param quartzTime
     * @return boolean
     */
    boolean update(QuartzTime quartzTime);

    /**
     * 根据条件分页查询定时任务上次执行的时间记录列表
     * @param map
     * @return list
     */
    List<QuartzTime> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
