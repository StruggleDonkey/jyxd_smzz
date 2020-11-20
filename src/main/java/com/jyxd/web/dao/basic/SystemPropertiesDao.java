package com.jyxd.web.dao.basic;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemPropertiesDao {

    /**
     * 根据条件查询系统配置数据
     * @param map
     * @return
     */
    List<String> querySystemProperties(Map<String,Object> map);

    /**
     * 护理文书--护理单--出量--查询所有出量类型
     * @param map remark=出量类型
     * @return
     */
    List<Map<String,Object>> getOutTypeList(Map<String,Object> map);
}
