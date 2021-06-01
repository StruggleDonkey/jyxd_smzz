package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.CustomField;
import com.jyxd.web.data.basic.InOutAmount;
import org.springframework.stereotype.Repository;

import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

@Repository
public interface InOutAmountDao {

    /**
     * 新增一条出入量表（二表合一）记录
     *
     * @param inOutAmount
     * @return boolean
     */
    boolean insert(InOutAmount inOutAmount);

    /**
     * 根据主键id查询一条出入量表（二表合一）记录
     *
     * @param id
     * @return CustomField
     */
    InOutAmount queryData(String id);

    /**
     * 更新一条出入量表（二表合一）记录
     *
     * @param inOutAmount
     * @return boolean
     */
    boolean update(InOutAmount inOutAmount);

    /**
     * 根据条件分页出入量表（二表合一）记录列表
     *
     * @param map
     * @return list
     */
    List<InOutAmount> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     *
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表
     *
     * @param map
     * @return list
     */
    List<Map<String, Object>> getInOutAmountList(Map<String, Object> map);

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表数量
     *
     * @param map
     * @return
     */
    int getInOutAmountNum(Map<String, Object> map);

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表(新的直接查10个字段)
     *
     * @param map
     * @return list
     */
    List<Map<String, Object>> queryInOutAmountList(Map<String, Object> map);

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表(新的直接查10个字段)
     *
     * @param map
     * @return list
     */
    Map<String, Object> queryInOutAmountById(Map<String, Object> map);

    /**
     * 根据医嘱主键查询余量，从大到小排序
     *
     * @param map
     * @return
     */
    List<String> getAllowanceDosageByOrderNo(Map<String, Object> map);
}
