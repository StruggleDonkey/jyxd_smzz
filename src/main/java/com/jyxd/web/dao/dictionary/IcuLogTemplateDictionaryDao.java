package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.IcuLogTemplateDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IcuLogTemplateDictionaryDao {

    /**
     * 新增一条ICU日志模板类型表记录
     * @param icuLogTemplateDictionary
     * @return boolean
     */
    boolean insert(IcuLogTemplateDictionary icuLogTemplateDictionary);

    /**
     * 根据主键id查询一条ICU日志模板类型表记录
     * @param id
     * @return IcuLogTemplateDictionary
     */
    IcuLogTemplateDictionary queryData(String id);

    /**
     * 更新一条ICU日志模板类型表记录
     * @param icuLogTemplateDictionary
     * @return boolean
     */
    boolean update(IcuLogTemplateDictionary icuLogTemplateDictionary);

    /**
     * 根据条件分页查询ICU日志模板类型表记录列表
     * @param map
     * @return list
     */
    List<IcuLogTemplateDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
