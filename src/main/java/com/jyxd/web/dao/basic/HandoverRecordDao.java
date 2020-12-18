package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.HandoverRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HandoverRecordDao {

    /**
     * 新增一条交接班记录明细表记录
     * @param handoverRecord
     * @return boolean
     */
    boolean insert(HandoverRecord handoverRecord);

    /**
     * 根据主键id查询一条交接班记录明细表记录
     * @param id
     * @return HandoverRecord
     */
    HandoverRecord queryData(String id);

    /**
     * 更新一条交接班记录明细表记录
     * @param handoverRecord
     * @return boolean
     */
    boolean update(HandoverRecord handoverRecord);

    /**
     * 根据条件分页查询交接班记录明细表记录列表
     * @param map
     * @return list
     */
    List<HandoverRecord> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据交接班主键查询交接班记录列表
     * @param handoverId
     * @return
     */
     List<HandoverRecord> queryListByHandoverId(String handoverId);

}
