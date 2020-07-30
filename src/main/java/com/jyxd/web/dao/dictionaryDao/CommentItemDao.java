package com.jyxd.web.dao.dictionaryDao;

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
}
