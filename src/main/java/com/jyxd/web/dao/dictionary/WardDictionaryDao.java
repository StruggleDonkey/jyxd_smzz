package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.WardDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WardDictionaryDao {

    /**
     * 新增一条病区字典表记录
     * @param wardDictionary
     * @return boolean
     */
    boolean insert(WardDictionary wardDictionary);

    /**
     * 根据主键id查询一条病区字典表记录
     * @param id
     * @return BedDictionary
     */
    WardDictionary queryData(String id);

    /**
     * 更新一条病区字典表记录
     * @param wardDictionary
     * @return boolean
     */
    boolean update(WardDictionary wardDictionary);

    /**
     * 根据条件分页查询病区字典表列表
     * @param map
     * @return list
     */
    List<WardDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
