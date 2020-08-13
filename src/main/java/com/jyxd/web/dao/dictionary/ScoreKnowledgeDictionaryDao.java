package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.ScoreKnowledgeDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreKnowledgeDictionaryDao {

    /**
     * 新增一条评分知识库表记录
     * @param scoreKnowledgeDictionary
     * @return boolean
     */
    boolean insert(ScoreKnowledgeDictionary scoreKnowledgeDictionary);

    /**
     * 根据主键id查询一条评分知识库表记录
     * @param id
     * @return ScoreKnowledgeDictionary
     */
    ScoreKnowledgeDictionary queryData(String id);

    /**
     * 更新一条评分知识库表记录
     * @param scoreKnowledgeDictionary
     * @return boolean
     */
    boolean update(ScoreKnowledgeDictionary scoreKnowledgeDictionary);

    /**
     * 根据条件分页查询评分知识库表记录列表
     * @param map
     * @return list
     */
    List<ScoreKnowledgeDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
