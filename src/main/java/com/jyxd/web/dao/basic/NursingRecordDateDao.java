package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.NursingRecordDate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NursingRecordDateDao {

    /**
     * 新增一条护理单日期表记录
     * @param nursingRecordDate
     * @return boolean
     */
    boolean insert(NursingRecordDate nursingRecordDate);

    /**
     * 根据主键id查询一条护理单日期表记录
     * @param id
     * @return NursingRecordDate
     */
    NursingRecordDate queryData(String id);

    /**
     * 更新一条护理单日期表记录
     * @param nursingRecordDate
     * @return boolean
     */
    boolean update(NursingRecordDate nursingRecordDate);

    /**
     * 根据条件分页查询护理单日期表记录列表
     * @param map
     * @return list
     */
    List<NursingRecordDate> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
