package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.TemplateDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TemplateDictionaryDao {

    /**
     * 新增一条护理模板类型表记录
     * @param templateDictionary
     * @return boolean
     */
    boolean insert(TemplateDictionary templateDictionary);

    /**
     * 根据主键id查询一条护理模板类型表记录
     * @param id
     * @return TemplateDictionary
     */
    TemplateDictionary queryData(String id);

    /**
     * 更新一条护理模板类型表记录
     * @param templateDictionary
     * @return boolean
     */
    boolean update(TemplateDictionary templateDictionary);

    /**
     * 根据条件分页查询护理模板类型表列表
     * @param map
     * @return list
     */
    List<TemplateDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

}
