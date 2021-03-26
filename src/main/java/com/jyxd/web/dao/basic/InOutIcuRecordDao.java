package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.InOutIcuRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InOutIcuRecordDao {

    /**
     * 新增一条病人出入科记录表记录
     * @param inOutIcuRecord
     * @return boolean
     */
    boolean insert(InOutIcuRecord inOutIcuRecord);

    /**
     * 根据主键id查询一条病人出入科记录表记录
     * @param id
     * @return InOutIcuRecord
     */
    InOutIcuRecord queryData(String id);

    /**
     * 更新一条病人出入科记录表记录
     * @param inOutIcuRecord
     * @return boolean
     */
    boolean update(InOutIcuRecord inOutIcuRecord);

    /**
     * 根据条件分页查询病人出入科记录表列表
     * @param map
     * @return list
     */
    List<InOutIcuRecord> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件查询病人出入科记录表对象
     * @param map
     * @return list
     */
    List<InOutIcuRecord> geList(Map<String, Object> map);
}
