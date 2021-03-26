package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.InputAllowance;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InputAllowanceDao {

    /**
     * 新增一条入量余量表记录
     * @param inputAllowance
     * @return boolean
     */
    boolean insert(InputAllowance inputAllowance);

    /**
     * 根据主键id查询一条入量余量表记录
     * @param id
     * @return InputAllowance
     */
    InputAllowance queryData(String id);

    /**
     * 更新一条入量余量表记录
     * @param inputAllowance
     * @return boolean
     */
    boolean update(InputAllowance inputAllowance);

    /**
     * 根据条件分页查询入量余量表记录列表
     * @param map
     * @return list
     */
    List<InputAllowance> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据医嘱主键查询入量余量列表
     * @param map
     * @return
     */
     List<InputAllowance> queryListByOrderNo(Map<String,Object> map);

    /**
     * 根据医嘱主键查询余量 从大到小排序
     * @param map
     * @return
     */
     List<String> getAllowanceDosageByOrderNo(Map<String,Object> map);

}
