package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.CostDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CostDetailsDao {

    /**
     * 新增一条费用明细表记录
     * @param costDetails
     * @return boolean
     */
    boolean insert(CostDetails costDetails);

    /**
     * 根据主键id查询一条费用明细表记录
     * @param id
     * @return CostDetails
     */
    CostDetails queryData(String id);

    /**
     * 更新一条费用明细表记录
     * @param costDetails
     * @return boolean
     */
    boolean update(CostDetails costDetails);

    /**
     * 根据条件分页查询费用明细表记录列表
     * @param map
     * @return list
     */
    List<CostDetails> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

}
