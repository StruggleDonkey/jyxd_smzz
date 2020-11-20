package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.TemplateDictionaryDao;
import com.jyxd.web.data.dictionary.TemplateDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TemplateDictionaryService {

    @Autowired
    private TemplateDictionaryDao templateDictionaryDao;

    public boolean insert(TemplateDictionary templateDictionary){
        return templateDictionaryDao.insert(templateDictionary);
    }

    public boolean update(TemplateDictionary templateDictionary){
        return templateDictionaryDao.update(templateDictionary);
    }

    public TemplateDictionary queryData(String id){
        return templateDictionaryDao.queryData(id);
    }

    public List<TemplateDictionary> queryList(Map<String,Object> map){
        return templateDictionaryDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return templateDictionaryDao.queryNum(map);}

}
