package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.CommonDictionaryDao;
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

    public CommonDictionary queryDataByName(Map<String,Object> map){
        return commonDictionaryDao.queryDataByName(map);
    }

    public int queryNum(Map<String,Object> map){return commonDictionaryDao.queryNum(map);}

    /**
     * 根据名称获取type
     * @param map
     * @return
     */
    public String queryTypeByName(Map<String,Object> map){
        return commonDictionaryDao.queryTypeByName(map);
    }
}
