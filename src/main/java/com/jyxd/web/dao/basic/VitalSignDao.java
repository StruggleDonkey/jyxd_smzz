package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.VitalSign;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VitalSignDao {

    /**
     * 新增一条生命体征表记录
     * @param vitalSign
     * @return boolean
     */
    boolean insert(VitalSign vitalSign);

    /**
     * 根据主键id查询一条生命体征表记录
     * @param id
     * @return VitalSign
     */
    VitalSign queryData(String id);

    /**
     * 更新一条生命体征表记录
     * @param vitalSign
     * @return boolean
     */
    boolean update(VitalSign vitalSign);

    /**
     * 根据条件分页查询生命体征表记录列表
     * @param map
     * @return list
     */
    List<VitalSign> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据时间和code查询对象
     * @param map
     * @return
     */
     VitalSign queryDataByTimeAndCode(Map<String,Object> map);

    /**
     * 根据时间和病人主键id查询对象列表
     * @param map
     * @return
     */
     List<VitalSign> queryListByTime(Map<String,Object> map);

    /**
     * 护理文书--护理单--生命体征--根据病人主键id 查询生命体征列表
     * @return
     */
     List<Map<String,Object>> getList(Map<String,Object> map);
}
