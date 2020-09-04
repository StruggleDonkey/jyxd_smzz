package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.AbnormalVitalSigns;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AbnormalVitalSignsDao {

    /**
     * 新增一条异常生命体征表记录
     * @param abnormalVitalSigns
     * @return boolean
     */
    boolean insert(AbnormalVitalSigns abnormalVitalSigns);

    /**
     * 根据主键id查询一条异常生命体征表记录
     * @param id
     * @return AbnormalVitalSigns
     */
    AbnormalVitalSigns queryData(String id);

    /**
     * 更新一条异常生命体征表记录
     * @param abnormalVitalSigns
     * @return boolean
     */
    boolean update(AbnormalVitalSigns abnormalVitalSigns);

    /**
     * 根据条件分页查询异常生命体征表记录列表
     * @param map
     * @return list
     */
    List<AbnormalVitalSigns> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
