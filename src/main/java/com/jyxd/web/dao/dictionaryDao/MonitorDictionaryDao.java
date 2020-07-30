package com.jyxd.web.dao.dictionaryDao;

import com.jyxd.web.data.dictionary.MonitorDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MonitorDictionaryDao {

    /**
     * 新增一条监护仪字典表记录
     * @param monitorDictionary
     * @return boolean
     */
    boolean insert(MonitorDictionary monitorDictionary);

    /**
     * 根据主键id查询一条监护仪字典表记录
     * @param id
     * @return MonitorDictionary
     */
    MonitorDictionary queryData(String id);

    /**
     * 更新一条监护仪字典表记录
     * @param monitorDictionary
     * @return boolean
     */
    boolean update(MonitorDictionary monitorDictionary);

    /**
     * 根据条件分页查询监护仪字典表列表
     * @param map
     * @return list
     */
    List<MonitorDictionary> queryList(Map<String, Object> map);
}
