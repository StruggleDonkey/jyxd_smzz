package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.BedDictionaryDao;
import com.jyxd.web.data.dictionary.BedDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BedDictionaryService {

    @Autowired
    private BedDictionaryDao bedDictionaryDao;

    public boolean insert(BedDictionary bedDictionary){
        return bedDictionaryDao.insert(bedDictionary);
    }

    public boolean update(BedDictionary bedDictionary){
        return bedDictionaryDao.update(bedDictionary);
    }

    public BedDictionary queryData(String id){
        return bedDictionaryDao.queryData(id);
    }

    public List<BedDictionary> queryList(Map<String,Object> map){
        return bedDictionaryDao.queryList(map);
    }
}
