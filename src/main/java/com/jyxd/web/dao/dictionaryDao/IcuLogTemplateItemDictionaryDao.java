package com.jyxd.web.dao.dictionaryDao;

import com.jyxd.web.data.dictionary.IcuLogTemplateItemDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IcuLogTemplateItemDictionaryDao {

    /**
     * 新增一条ICU日志模板表记录
     * @param icuLogTemplateItemDictionary
     * @return boolean
     */
    boolean insert(IcuLogTemplateItemDictionary icuLogTemplateItemDictionary);

    /**
     * 根据主键id查询一条ICU日志模板表记录
     * @param id
     * @return BedDictionary
     */
    IcuLogTemplateItemDictionary queryData(String id);

    /**
     * 更新一条ICU日志模板表记录
     * @param icuLogTemplateItemDictionary
     * @return boolean
     */
    boolean update(IcuLogTemplateItemDictionary icuLogTemplateItemDictionary);

    /**
     * 根据条件分页查询ICU日志模板表记录列表
     * @param map
     * @return list
     */
    List<IcuLogTemplateItemDictionary> queryList(Map<String, Object> map);
}
