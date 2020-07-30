package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.ScoreKnowledgeDictionaryDao;
import com.jyxd.web.data.dictionary.ScoreKnowledgeDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScoreKnowledgeDictionaryService {

    @Autowired
    private ScoreKnowledgeDictionaryDao scoreKnowledgeDictionaryDao;

    public boolean insert(ScoreKnowledgeDictionary scoreKnowledgeDictionary){
        return scoreKnowledgeDictionaryDao.insert(scoreKnowledgeDictionary);
    }

    public boolean update(ScoreKnowledgeDictionary scoreKnowledgeDictionary){
        return scoreKnowledgeDictionaryDao.update(scoreKnowledgeDictionary);
    }

    public ScoreKnowledgeDictionary queryData(String id){
        return scoreKnowledgeDictionaryDao.queryData(id);
    }

    public List<ScoreKnowledgeDictionary> queryList(Map<String,Object> map){
        return scoreKnowledgeDictionaryDao.queryList(map);
    }
}
