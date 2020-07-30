package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.IcuLogTemplateDictionaryDao;
import com.jyxd.web.data.dictionary.IcuLogTemplateDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IcuLogTemplateDictionaryService {

    @Autowired
    private IcuLogTemplateDictionaryDao icuLogTemplateDictionaryDao;

    public boolean insert(IcuLogTemplateDictionary icuLogTemplateDictionary){
        return icuLogTemplateDictionaryDao.insert(icuLogTemplateDictionary);
    }

    public boolean update(IcuLogTemplateDictionary icuLogTemplateDictionary){
        return icuLogTemplateDictionaryDao.update(icuLogTemplateDictionary);
    }

    public IcuLogTemplateDictionary queryData(String id){
        return icuLogTemplateDictionaryDao.queryData(id);
    }

    public List<IcuLogTemplateDictionary> queryList(Map<String,Object> map){
        return icuLogTemplateDictionaryDao.queryList(map);
    }
}
