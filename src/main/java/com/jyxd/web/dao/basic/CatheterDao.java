package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.Catheter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CatheterDao {

    /**
     * 新增一条导管基本信息表记录
     * @param catheter
     * @return boolean
     */
    boolean insert(Catheter catheter);

    /**
     * 根据主键id查询一条导管基本信息表记录
     * @param id
     * @return Catheter
     */
    Catheter queryData(String id);

    /**
     * 更新一条导管基本信息表记录
     * @param catheter
     * @return boolean
     */
    boolean update(Catheter catheter);

    /**
     * 根据条件分页查询导管基本信息表记录列表
     * @param map
     * @return list
     */
    List<Catheter> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
