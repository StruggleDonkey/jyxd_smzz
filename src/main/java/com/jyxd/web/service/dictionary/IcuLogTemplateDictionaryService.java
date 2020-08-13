package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.IcuLogTemplateDictionaryDao;
import com.jyxd.web.data.dictionary.IcuLogTemplateDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
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

    public int queryNum(Map<String,Object> map){return icuLogTemplateDictionaryDao.queryNum(map);}
}
