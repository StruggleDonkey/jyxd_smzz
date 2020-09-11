package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BloodGas;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BloodGasDao {

    /**
     * 新增一条血气表记录
     * @param bloodGas
     * @return boolean
     */
    boolean insert(BloodGas bloodGas);

    /**
     * 根据主键id查询一条血气表记录
     * @param id
     * @return BloodGas
     */
    BloodGas queryData(String id);

    /**
     * 更新一条血气表记录
     * @param bloodGas
     * @return boolean
     */
    boolean update(BloodGas bloodGas);

    /**
     * 根据条件分页查询血气表记录列表
     * @param map
     * @return list
     */
    List<BloodGas> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
