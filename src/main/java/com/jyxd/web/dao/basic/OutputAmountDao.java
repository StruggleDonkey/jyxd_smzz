package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.OutputAmount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OutputAmountDao {

    /**
     * 新增一条出量表记录
     * @param outputAmount
     * @return boolean
     */
    boolean insert(OutputAmount outputAmount);

    /**
     * 根据主键id查询一条出量表记录
     * @param id
     * @return OutputAmount
     */
    OutputAmount queryData(String id);

    /**
     * 更新一条出量表记录
     * @param outputAmount
     * @return boolean
     */
    boolean update(OutputAmount outputAmount);

    /**
     * 根据条件分页查询出量表记录列表
     * @param map
     * @return list
     */
    List<OutputAmount> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
