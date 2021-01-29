package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.Operation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OperationDao {

    /**
     * 新增一条手术信息表记录
     * @param operation
     * @return boolean
     */
    boolean insert(Operation operation);

    /**
     * 根据主键id查询一条手术信息表记录
     * @param id
     * @return Operation
     */
    Operation queryData(String id);

    /**
     * 更新一条手术信息表记录
     * @param operation
     * @return boolean
     */
    boolean update(Operation operation);

    /**
     * 根据条件分页查询手术信息表记录列表
     * @param map
     * @return list
     */
    List<Operation> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 查询所有手术信息 status!=-1
     * @param map
     * @return
     */
     List<Operation> queryOperationList(Map<String,Object> map);
}
