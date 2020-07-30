package com.jyxd.web.dao.dictionaryDao;

import com.jyxd.web.data.dictionary.CommonDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommonDictionaryDao {

    /**
     * 新增一条通用字典类型表记录
     * @param commonDictionary
     * @return boolean
     */
    boolean insert(CommonDictionary commonDictionary);

    /**
     * 根据主键id查询一条通用字典类型表记录
     * @param id
     * @return CommonDictionary
     */
    CommonDictionary queryData(String id);

    /**
     * 更新一条通用字典类型表记录
     * @param commonDictionary
     * @return boolean
     */
    boolean update(CommonDictionary commonDictionary);

    /**
     * 根据条件分页查询通用字典类型记录列表
     * @param map
     * @return list
     */
    List<CommonDictionary> queryList(Map<String, Object> map);
}
