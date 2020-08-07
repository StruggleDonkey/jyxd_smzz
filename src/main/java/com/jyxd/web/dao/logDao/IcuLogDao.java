package com.jyxd.web.dao.logDao;

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
}
