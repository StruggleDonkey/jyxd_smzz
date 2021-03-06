package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.ScoreDictionaryDao;
import com.jyxd.web.data.dictionary.ScoreDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScoreDictionaryService {

    @Autowired
    private ScoreDictionaryDao scoreDictionaryDao;

    public boolean insert(ScoreDictionary scoreDictionary){
        return scoreDictionaryDao.insert(scoreDictionary);
    }

    public boolean update(ScoreDictionary scoreDictionary){
        return scoreDictionaryDao.update(scoreDictionary);
    }

    public ScoreDictionary queryData(String id){
        return scoreDictionaryDao.queryData(id);
    }

    public List<ScoreDictionary> queryList(Map<String,Object> map){
        return scoreDictionaryDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return scoreDictionaryDao.queryNum(map);}

    /**
     * 根据评分类型查询评分对象 如：评分类型
     * @param map
     * @return
     */
    public ScoreDictionary queryDataByType(Map<String,Object> map){
        return scoreDictionaryDao.queryDataByType(map);
    }

    /**
     * 根据评分名称查询评分对象
     * @param map
     * @return
     */
    public ScoreDictionary queryDataByName(Map<String,Object> map){
        return scoreDictionaryDao.queryDataByName(map);
    }
}
