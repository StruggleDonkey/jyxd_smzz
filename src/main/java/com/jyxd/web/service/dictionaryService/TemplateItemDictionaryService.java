package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.TemplateItemDictionaryDao;
import com.jyxd.web.data.dictionary.TemplateItemDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TemplateItemDictionaryService {

    @Autowired
    private TemplateItemDictionaryDao templateItemDictionaryDao;

    public boolean insert(TemplateItemDictionary templateItemDictionary){
        return templateItemDictionaryDao.insert(templateItemDictionary);
    }

    public boolean update(TemplateItemDictionary templateItemDictionary){
        return templateItemDictionaryDao.update(templateItemDictionary);
    }

    public TemplateItemDictionary queryData(String id){
        return templateItemDictionaryDao.queryData(id);
    }

    public List<TemplateItemDictionary> queryList(Map<String,Object> map){
        return templateItemDictionaryDao.queryList(map);
    }
}
