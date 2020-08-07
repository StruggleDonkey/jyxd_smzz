package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.IcuLogTemplateItemDictionaryDao;
import com.jyxd.web.data.dictionary.IcuLogTemplateItemDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IcuLogTemplateItemDictionaryService {

    @Autowired
    private IcuLogTemplateItemDictionaryDao icuLogTemplateItemDictionaryDao;

    public boolean insert(IcuLogTemplateItemDictionary icuLogTemplateItemDictionary){
        return icuLogTemplateItemDictionaryDao.insert(icuLogTemplateItemDictionary);
    }

    public boolean update(IcuLogTemplateItemDictionary icuLogTemplateItemDictionary){
        return icuLogTemplateItemDictionaryDao.update(icuLogTemplateItemDictionary);
    }

    public IcuLogTemplateItemDictionary queryData(String id){
        return icuLogTemplateItemDictionaryDao.queryData(id);
    }

    public List<IcuLogTemplateItemDictionary> queryList(Map<String,Object> map){
        return icuLogTemplateItemDictionaryDao.queryList(map);
    }
}
