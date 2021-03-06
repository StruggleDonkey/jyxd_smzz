package com.jyxd.web.dao.log;

import com.jyxd.web.data.log.Log;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LogDao {

    /**
     * 新增一条操作日志表记录
     * @param log
     * @return boolean
     */
    boolean insert(Log log);

    /**
     * 根据主键id查询一条操作日志表记录
     * @param id
     * @return Log
     */
    Log queryData(String id);

    /**
     * 更新一条操作日志表记录
     * @param log
     * @return boolean
     */
    boolean update(Log log);

    /**
     * 根据条件分页查询操作日志表记录列表
     * @param map
     * @return list
     */
    List<Log> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询操作日志表记录列表--多表
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
}
