package com.jyxd.web.dao.dictionaryDao;

import com.jyxd.web.data.dictionary.ScoreItemDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreItemDictionaryDao {

    /**
     * 新增一条评分项字典表记录
     * @param scoreItemDictionary
     * @return boolean
     */
    boolean insert(ScoreItemDictionary scoreItemDictionary);

    /**
     * 根据主键id查询一条评分项字典表记录
     * @param id
     * @return ScoreItemDictionary
     */
    ScoreItemDictionary queryData(String id);

    /**
     * 更新一条评分项字典表记录
     * @param scoreItemDictionary
     * @return boolean
     */
    boolean update(ScoreItemDictionary scoreItemDictionary);

    /**
     * 根据条件分页查询评分项字典表记录列表
     * @param map
     * @return list
     */
    List<ScoreItemDictionary> queryList(Map<String, Object> map);
}
