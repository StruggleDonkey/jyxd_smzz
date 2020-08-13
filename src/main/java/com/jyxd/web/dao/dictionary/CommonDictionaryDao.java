package com.jyxd.web.dao.dictionary;

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

    /**
     * 根据名称或字典项类型查询对象
     * @param map
     * @return
     */
    CommonDictionary queryDataByName(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
