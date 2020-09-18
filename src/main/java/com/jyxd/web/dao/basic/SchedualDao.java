package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.Schedual;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SchedualDao {

    /**
     * 新增一条人员排班表记录
     * @param schedual
     * @return boolean
     */
    boolean insert(Schedual schedual);

    /**
     * 根据主键id查询一条人员排班表记录
     * @param id
     * @return Schedual
     */
    Schedual queryData(String id);

    /**
     * 更新一条人员排班表记录
     * @param schedual
     * @return boolean
     */
    boolean update(Schedual schedual);

    /**
     * 根据条件分页查询人员排班表记录列表
     * @param map
     * @return list
     */
    List<Schedual> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
