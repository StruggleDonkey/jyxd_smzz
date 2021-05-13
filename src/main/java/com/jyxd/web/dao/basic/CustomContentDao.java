package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.CustomContent;
import com.jyxd.web.data.basic.CustomField;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomContentDao {

    /**
     * 新增一条自定义字段数据内容表记录
     * @param customContent
     * @return boolean
     */
    boolean insert(CustomContent customContent);

    /**
     * 根据主键id查询一条自定义字段数据内容表记录
     * @param id
     * @return CustomContent
     */
    CustomContent queryData(String id);

    /**
     * 更新一条自定义字段数据内容表记录
     * @param customContent
     * @return boolean
     */
    boolean update(CustomContent customContent);

    /**
     * 根据条件分页自定义字段数据内容表记录列表
     * @param map
     * @return list
     */
    List<CustomContent> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据时间 病人主键id 表名 查询对象
     * @param map
     * @return
     */
    List<CustomContent> getCustomContentByTime(Map<String, Object> map);
}
