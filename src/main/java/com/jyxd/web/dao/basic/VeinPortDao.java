package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.VeinPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VeinPortDao {

    /**
     * 新增一条静脉输液港基本信息表记录
     * @param veinPort
     * @return boolean
     */
    boolean insert(VeinPort veinPort);

    /**
     * 根据主键id查询一条静脉输液港基本信息表记录
     * @param id
     * @return VeinPort
     */
    VeinPort queryData(String id);

    /**
     * 更新一条静脉输液港基本信息表记录
     * @param veinPort
     * @return boolean
     */
    boolean update(VeinPort veinPort);

    /**
     * 根据条件分页查询静脉输液港基本信息表记录列表
     * @param map
     * @return list
     */
    List<VeinPort> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
