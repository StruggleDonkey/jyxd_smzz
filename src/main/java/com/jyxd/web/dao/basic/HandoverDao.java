package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.Handover;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HandoverDao {

    /**
     * 新增一条交班记录主表记录
     * @param handover
     * @return boolean
     */
    boolean insert(Handover handover);

    /**
     * 根据主键id查询一条交班记录主表记录
     * @param id
     * @return Handover
     */
    Handover queryData(String id);

    /**
     * 更新一条交班记录主表记录
     * @param handover
     * @return boolean
     */
    boolean update(Handover handover);

    /**
     * 根据条件分页查询交班记录主表记录列表
     * @param map
     * @return list
     */
    List<Handover> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

}
