package com.jyxd.web.dao.log;

import com.jyxd.web.data.log.IcuLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IcuLogDao {

    /**
     * 新增一条ICU日志表记录
     * @param icuLog
     * @return boolean
     */
    boolean insert(IcuLog icuLog);

    /**
     * 根据主键id查询一条ICU日志表记录
     * @param id
     * @return IcuLog
     */
    IcuLog queryData(String id);

    /**
     * 更新一条ICU日志表记录
     * @param icuLog
     * @return boolean
     */
    boolean update(IcuLog icuLog);

    /**
     * 根据条件分页查询ICU日志表记录列表
     * @param map
     * @return list
     */
    List<IcuLog> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询ICU日志表记录列表-多表
     * @param map
     * @return list
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数-多表
     * @param map
     * @return
     */
    int getNum(Map<String, Object> map);

    /**
     * 病人管理-患者日志-日志列表-编辑回显-根据主键id查询对象详情
     * @param map
     * @return
     */
     Map<String,Object> getEditData(Map<String,Object> map);
}
