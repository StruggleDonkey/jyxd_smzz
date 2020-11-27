package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.InputAmount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InputAmountDao {

    /**
     * 新增一条入量表记录
     * @param inputAmount
     * @return boolean
     */
    boolean insert(InputAmount inputAmount);

    /**
     * 根据主键id查询一条入量表记录
     * @param id
     * @return InputAmount
     */
    InputAmount queryData(String id);

    /**
     * 更新一条入量表记录
     * @param inputAmount
     * @return boolean
     */
    boolean update(InputAmount inputAmount);

    /**
     * 根据条件分页查询入量表记录列表
     * @param map
     * @return list
     */
    List<InputAmount> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据时间查询入量对象
     * @param map
     * @return
     */
    InputAmount queryDataByTime(Map<String, Object> map);

    /**
     * 护理文书--护理单--入量--根据病人id查询入量列表
     * @param map
     * @return
     */
     List<Map<String,Object>> getListByPatientId(Map<String,Object> map);
}
