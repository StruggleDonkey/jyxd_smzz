package com.jyxd.web.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatientTestDao {

    /**
     * 根据条件查询结果
     * @param map
     * @return list
     */
    List<Map<String, Object>> queryListTest(Map<String, Object> map);

}
