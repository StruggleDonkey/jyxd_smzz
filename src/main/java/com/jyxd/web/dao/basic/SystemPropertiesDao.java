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
}
