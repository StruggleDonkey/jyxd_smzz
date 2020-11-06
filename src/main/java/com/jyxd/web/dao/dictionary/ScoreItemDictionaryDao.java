package com.jyxd.web.dao.dictionary;

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

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询评分项字典表子评分明细记录列表（也可以不分页）
     * @param map
     * @return
     */
    List<Map<String, Object>> querySonList(Map<String, Object> map);

    /**
     * 根据条件查询子评分项列表总记录数
     * @param map
     * @return
     */
    int querySonNum(Map<String, Object> map);

    /**
     * 根据type 查询 评分项列表 （parent_id为空的情况）
     * @param map
     * @return
     */
    List<ScoreItemDictionary> queryParentListByType(Map<String,Object> map);

    /**
     * 根据type 和parentId 查询 评分项列表 （parent_id不为空的情况）
     * @param map
     * @return
     */
    List<ScoreItemDictionary> querySonListByType(Map<String,Object> map);
}
