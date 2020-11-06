package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BloodPurifyRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BloodPurifyRecordDao {

    /**
     * 新增一条血液净化维护记录表记录
     * @param bloodPurifyRecord
     * @return boolean
     */
    boolean insert(BloodPurifyRecord bloodPurifyRecord);

    /**
     * 根据主键id查询一条血液净化维护记录表记录
     * @param id
     * @return BloodPurifyRecord
     */
    BloodPurifyRecord queryData(String id);

    /**
     * 更新一条血液净化维护记录表记录
     * @param bloodPurifyRecord
     * @return boolean
     */
    boolean update(BloodPurifyRecord bloodPurifyRecord);

    /**
     * 根据条件分页查询血液净化维护记录表记录列表
     * @param map
     * @return list
     */
    List<BloodPurifyRecord> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

}
