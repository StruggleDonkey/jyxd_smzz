package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.VeinPortMaintain;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VeinPortMaintainDao {

    /**
     * 新增一条静脉输液港维护记录表记录
     * @param veinPortMaintain
     * @return boolean
     */
    boolean insert(VeinPortMaintain veinPortMaintain);

    /**
     * 根据主键id查询一条静脉输液港维护记录表记录
     * @param id
     * @return VeinPortMaintain
     */
    VeinPortMaintain queryData(String id);

    /**
     * 更新一条静脉输液港维护记录表记录
     * @param veinPortMaintain
     * @return boolean
     */
    boolean update(VeinPortMaintain veinPortMaintain);

    /**
     * 根据条件分页查询静脉输液港维护记录表记录列表
     * @param map
     * @return list
     */
    List<VeinPortMaintain> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
