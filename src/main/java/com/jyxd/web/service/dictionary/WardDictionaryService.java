package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.WardDictionaryDao;
import com.jyxd.web.data.dictionary.WardDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
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

    public int queryNum(Map<String,Object> map){return wardDictionaryDao.queryNum(map);}

    /**
     * 根据code查询病区字典对象
     * @param map
     * @return
     */
    public WardDictionary queryDataByCode(Map<String,Object> map){
        return wardDictionaryDao.queryDataByCode(map);
    }
}
