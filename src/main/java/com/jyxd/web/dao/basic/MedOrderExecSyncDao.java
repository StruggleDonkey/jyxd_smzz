package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.MedOrderExecSync;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MedOrderExecSyncDao {

    /**
     * 新增一条医嘱执行同步表记录
     * @param medOrderExecSync
     * @return boolean
     */
    boolean insert(MedOrderExecSync medOrderExecSync);

    /**
     * 根据主键id查询一条医嘱执行同步表记录
     * @param id
     * @return MedOrderExecSync
     */
    MedOrderExecSync queryData(String id);

    /**
     * 更新一条医嘱执行同步表记录
     * @param medOrderExecSync
     * @return boolean
     */
    boolean update(MedOrderExecSync medOrderExecSync);

    /**
     * 根据条件分页查询医嘱执行同步表记录列表
     * @param map
     * @return list
     */
    List<MedOrderExecSync> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询医嘱执行同步表记录列表 多表
     * @param map
     * @return list
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数 多表
     * @param map
     * @return
     */
    int getNum(Map<String, Object> map);
}
