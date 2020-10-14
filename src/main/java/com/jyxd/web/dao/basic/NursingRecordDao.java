package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.NursingRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NursingRecordDao {

    /**
     * 新增一条护理记录表记录
     * @param nursingRecord
     * @return boolean
     */
    boolean insert(NursingRecord nursingRecord);

    /**
     * 根据主键id查询一条护理记录表记录
     * @param id
     * @return NursingRecord
     */
    NursingRecord queryData(String id);

    /**
     * 更新一条护理记录表记录
     * @param nursingRecord
     * @return boolean
     */
    boolean update(NursingRecord nursingRecord);

    /**
     * 根据条件分页查询护理记录表记录列表
     * @param map
     * @return list
     */
    List<NursingRecord> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 快捷录入--护理单--查询在科病人的护理信息
     * @param map
     * @return
     */
    List<Map<String, Object>> getListByTime(Map<String, Object> map);

    /**
     * 根据时间和code查询护理对象
     * @param map
     * @return
     */
    NursingRecord queryDataByTimeAndCode(Map<String, Object> map);
}
