package com.jyxd.web.dao.dictionary;

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

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询ICU日志模板表记录列表--多表查询
     * @param map
     * @return
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数--多表查询
     * @param map
     * @return
     */
    int getNum(Map<String, Object> map);
}
