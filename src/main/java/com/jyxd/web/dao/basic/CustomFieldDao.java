package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.AbnormalVitalSigns;
import com.jyxd.web.data.basic.CustomField;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomFieldDao {

    /**
     * 新增一条自定义字段名称表记录
     * @param customField
     * @return boolean
     */
    boolean insert(CustomField customField);

    /**
     * 根据主键id查询一条自定义字段名称表记录
     * @param id
     * @return CustomField
     */
    CustomField queryData(String id);

    /**
     * 更新一条自定义字段名称表记录
     * @param customField
     * @return boolean
     */
    boolean update(CustomField customField);

    /**
     * 根据条件分页自定义字段名称表记录列表
     * @param map
     * @return list
     */
    List<CustomField> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
