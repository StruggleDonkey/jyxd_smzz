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

    /**
     * 快捷录入--体温单--体温表单--根据时间查询在科病人体温表单数据
     * @param map
     * @return
     */
    List<Map<String,Object>> getListByTime(Map<String, Object> map);

    /**
     * 根据时间查询出量对象
     * @param map
     * @return
     */
    OutputAmount queryDataByTime(Map<String, Object> map);

    /**
     * 护理文书--护理单--出量--查询病人出量列表
     * @param map
     * @return
     */
     List<Map<String,Object>> getPatientOutputList(Map<String,Object> map);

    /**
     * 根据病人id查询病人在某个时间段的出量汇总信息
     * @param map
     * @return
     */
    Map<String,Object> getOutAmount(Map<String,Object> map);

    /**
     * 查询病人某段时间内的出量总数
     * @param map
     * @return
     */
     String getOutTotal(Map<String,Object> map);

    /**
     * 查询病人某段时间内各个出量类型的入量总数
     * @param map
     * @return
     */
     List<Map<String,Object>> getOutAnalyze(Map<String,Object> map);
}
