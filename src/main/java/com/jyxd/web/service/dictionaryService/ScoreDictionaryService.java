package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.ScoreDictionaryDao;
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
}
