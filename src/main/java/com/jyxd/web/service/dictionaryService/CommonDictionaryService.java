package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.CommonDictionaryDao;
import com.jyxd.web.data.dictionary.CommonDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommonDictionaryService {

    @Autowired
    private CommonDictionaryDao commonDictionaryDao;

    public boolean insert(CommonDictionary commonDictionary){
        return commonDictionaryDao.insert(commonDictionary);
    }

    public boolean update(CommonDictionary commonDictionary){
        return commonDictionaryDao.update(commonDictionary);
    }

    public CommonDictionary queryData(String id){
        return commonDictionaryDao.queryData(id);
    }

    public List<CommonDictionary> queryList(Map<String,Object> map){
        return commonDictionaryDao.queryList(map);
    }
}
