package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BloodSugar;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BloodSugarDao {

    /**
     * 新增一条血糖表记录
     * @param bloodSugar
     * @return boolean
     */
    boolean insert(BloodSugar bloodSugar);

    /**
     * 根据主键id查询一条血糖表记录
     * @param id
     * @return BloodSugar
     */
    BloodSugar queryData(String id);

    /**
     * 更新一条血糖表记录
     * @param bloodSugar
     * @return boolean
     */
    boolean update(BloodSugar bloodSugar);

    /**
     * 根据条件分页查询血糖表记录列表
     * @param map
     * @return list
     */
    List<BloodSugar> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据时间查询在科病人的血糖信息
     * @param map
     * @return
     */
    List<Map<String, Object>> getListByTime(Map<String, Object> map);

    /**
     * 根据病人id code 时间 查询血糖对象
     * @param map
     * @return
     */
     BloodSugar queryDataByCodeAndPatientId(Map<String,Object> map);

    /**
     * 根据时间和病人id查询血糖列表
     * @param map
     * @return
     */
     List<BloodSugar> queryListByTimeAndPatientId(Map<String,Object> map);

    /**
     * 护理文书--血糖监测--根据病人id 和 时间查询血糖列表
     * @param map
     * @return
     */
     List<Map<String,Object>> getList(Map<String,Object> map);
}
