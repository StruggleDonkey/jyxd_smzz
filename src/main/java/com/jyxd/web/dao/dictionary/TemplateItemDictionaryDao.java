package com.jyxd.web.dao.dictionary;

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

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询护理模板表记录列表（也可以不分页、多表查询）
     * @param map
     * @return
     */
    List<Map<String,Object>> getList(Map<String,Object> map);

    /**
     * 根据条件查询列表总记录数--多表查询
     * @param map
     * @return
     */
    int getNum(Map<String,Object> map);

    /**
     * 重症评分--护理单--护理记录--查询护理模板名称列表及其数量
     * @param map
     * @return
     */
     List<Map<String,Object>> getTemplateNameAndAmount(Map<String,Object> map);
}
