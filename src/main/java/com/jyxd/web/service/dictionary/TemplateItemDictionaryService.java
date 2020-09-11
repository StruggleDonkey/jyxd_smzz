package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.TemplateItemDictionaryDao;
import com.jyxd.web.data.dictionary.TemplateItemDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
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

    public int queryNum(Map<String,Object> map){return templateItemDictionaryDao.queryNum(map);}

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return templateItemDictionaryDao.getList(map);
    }

    public int getNum(Map<String,Object> map){
        return templateItemDictionaryDao.getNum(map);
    }
}
