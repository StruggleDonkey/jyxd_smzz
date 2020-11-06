package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.ScoreDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreDictionaryDao {

    /**
     * 新增一条评分字典表记录
     * @param scoreDictionary
     * @return boolean
     */
    boolean insert(ScoreDictionary scoreDictionary);

    /**
     * 根据主键id查询一条评分字典表记录
     * @param id
     * @return ScoreDictionary
     */
    ScoreDictionary queryData(String id);

    /**
     * 更新一条评分字典表记录
     * @param scoreDictionary
     * @return boolean
     */
    boolean update(ScoreDictionary scoreDictionary);

    /**
     * 根据条件分页查询评分字典表列表
     * @param map
     * @return list
     */
    List<ScoreDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据评分类型查询评分对象 如：评分类型
     * @param map
     * @return
     */
    ScoreDictionary queryDataByType(Map<String, Object> map);

    /**
     * 根据评分名称查询评分对象
     * @param map
     * @return
     */
    ScoreDictionary queryDataByName(Map<String,Object> map);
}
