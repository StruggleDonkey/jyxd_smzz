package com.jyxd.web.dao.dictionaryDao;

import com.jyxd.web.data.dictionary.TemplateItemDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TemplateItemDictionaryDao {

    /**
     * 新增一条护理模板表记录
     * @param templateItemDictionary
     * @return boolean
     */
    boolean insert(TemplateItemDictionary templateItemDictionary);

    /**
     * 根据主键id查询一条护理模板表记录
     * @param id
     * @return TemplateItemDictionary
     */
    TemplateItemDictionary queryData(String id);

    /**
     * 更新一条护理模板表记录
     * @param templateItemDictionary
     * @return boolean
     */
    boolean update(TemplateItemDictionary templateItemDictionary);

    /**
     * 根据条件分页查询护理模板表记录列表
     * @param map
     * @return list
     */
    List<TemplateItemDictionary> queryList(Map<String, Object> map);
}
