package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.CommenItemDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentItemDao {

    /**
     * 新增一条通用字典表记录
     * @param commenItemDictionary
     * @return boolean
     */
    boolean insert(CommenItemDictionary commenItemDictionary);

    /**
     * 根据主键id查询一条通用字典表记录
     * @param id
     * @return CommenItemDictionary
     */
    CommenItemDictionary queryData(String id);

    /**
     * 更新一条通用字典表记录
     * @param commenItemDictionary
     * @return boolean
     */
    boolean update(CommenItemDictionary commenItemDictionary);

    /**
     * 根据条件分页查询通用字典表记录列表
     * @param map
     * @return list
     */
    List<CommenItemDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件分页查询通用字典表记录列表 -- 多表查询
     * @param map
     * @return
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数-- 多表查询
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据type获取对象列表
     * @param map
     * @return
     */
     List<Map<String,Object>> getCodeListByType(Map<String,Object> map);

    /**
     * 根据codes 获取多个名称（以逗号分隔）
     * @param map
     * @return
     */
     String getNamesByCodes(Map<String,Object> map);

    /**
     * 根据type 获取多个名称（以 空格分隔）
     * @param map
     * @return
     */
     String getNamesByType(Map<String,Object> map);
}
