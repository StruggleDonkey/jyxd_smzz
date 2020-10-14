package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.MedOrderExec;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MedOrderExecDao {

    /**
     * 新增一条医嘱执行表记录
     * @param medOrderExec
     * @return boolean
     */
    boolean insert(MedOrderExec medOrderExec);

    /**
     * 根据主键id查询一条医嘱执行表记录
     * @param id
     * @return MedOrderExec
     */
    MedOrderExec queryData(String id);

    /**
     * 更新一条医嘱执行表记录
     * @param medOrderExec
     * @return boolean
     */
    boolean update(MedOrderExec medOrderExec);

    /**
     * 根据条件分页查询医嘱执行表记录列表
     * @param map
     * @return list
     */
    List<MedOrderExec> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}