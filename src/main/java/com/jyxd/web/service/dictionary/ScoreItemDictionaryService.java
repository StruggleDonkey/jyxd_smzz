package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.ScoreItemDictionaryDao;
import com.jyxd.web.data.dictionary.ScoreItemDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScoreItemDictionaryService {

    @Autowired
    private ScoreItemDictionaryDao scoreItemDictionaryDao;

    public boolean insert(ScoreItemDictionary scoreItemDictionary){
        return scoreItemDictionaryDao.insert(scoreItemDictionary);
    }

    public boolean update(ScoreItemDictionary scoreItemDictionary){
        return scoreItemDictionaryDao.update(scoreItemDictionary);
    }

    public ScoreItemDictionary queryData(String id){
        return scoreItemDictionaryDao.queryData(id);
    }

    public List<ScoreItemDictionary> queryList(Map<String,Object> map){
        return scoreItemDictionaryDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return scoreItemDictionaryDao.queryNum(map);}

    /**
     * 根据条件分页查询评分项字典表子评分明细记录列表（也可以不分页）
     * @param map
     * @return
     */
    public List<Map<String,Object>> querySonList(Map<String,Object> map){
        return scoreItemDictionaryDao.querySonList(map);
    }

    public int querySonNum(Map<String,Object> map){return scoreItemDictionaryDao.querySonNum(map);}
}
