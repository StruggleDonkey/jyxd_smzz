package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.CatheterMaintain;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CatheterMaintainDao {

    /**
     * 新增一条导管维护记录表记录
     * @param catheterMaintain
     * @return boolean
     */
    boolean insert(CatheterMaintain catheterMaintain);

    /**
     * 根据主键id查询一条导管维护记录表记录
     * @param id
     * @return CatheterMaintain
     */
    CatheterMaintain queryData(String id);

    /**
     * 更新一条导管维护记录表记录
     * @param catheterMaintain
     * @return boolean
     */
    boolean update(CatheterMaintain catheterMaintain);

    /**
     * 根据条件分页查询导管维护记录表记录列表
     * @param map
     * @return list
     */
    List<CatheterMaintain> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
