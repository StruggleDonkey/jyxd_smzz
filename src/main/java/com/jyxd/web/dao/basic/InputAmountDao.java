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

    int getNumByPatientId(Map<String,Object> map);

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

    /**
     * 根据病人id查询病人在某个时间段的入量汇总信息
     * @param map
     * @return
     */
    Map<String,Object> getInAmount(Map<String,Object> map);

    /**
     * 查询病人某段时间内的入量总数
     * @param map
     * @return
     */
     String getInTotal(Map<String,Object> map);

    /**
     * 查询病人某段时间内各个入量类型的入量总数
     * @param map
     * @return
     */
     List<Map<String,Object>> getInAnalyze(Map<String,Object> map);

    /**
     * 按天查询某段时间内病人出入量列表
     * @param map
     * @return
     */
    List<Map<String,Object>> getInAndOutAnalyzeByDay(Map<String,Object> map);

    /**
     * 按小时查询某段时间内病人出入量列表
     * @param map
     * @return
     */
     List<Map<String,Object>> getInAndOutAnalyzeByTime(Map<String,Object> map);

    /**
     * 护理文书-护理单文书-入量-核对签名-根据入量主键id查询出入量详情
     * @param map
     * @return
     */
     Map<String,Object> getDataDetailsById(Map<String,Object> map);
}
