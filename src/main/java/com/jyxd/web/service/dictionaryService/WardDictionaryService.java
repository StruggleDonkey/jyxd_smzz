package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.WardDictionaryDao;
import com.jyxd.web.data.dictionary.WardDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WardDictionaryService {

    @Autowired
    private WardDictionaryDao wardDictionaryDao;

    public boolean insert(WardDictionary wardDictionary){
        return wardDictionaryDao.insert(wardDictionary);
    }

    public boolean update(WardDictionary wardDictionary){
        return wardDictionaryDao.update(wardDictionary);
    }

    public WardDictionary queryData(String id){
        return wardDictionaryDao.queryData(id);
    }

    public List<WardDictionary> queryList(Map<String,Object> map){
        return wardDictionaryDao.queryList(map);
    }
}
