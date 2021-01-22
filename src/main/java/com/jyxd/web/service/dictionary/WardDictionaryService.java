package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.WardDictionaryDao;
import com.jyxd.web.data.dictionary.WardDictionary;
import com.jyxd.web.mapper.PatientTestDao;
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

    @Autowired
    private PatientTestDao patientTestDao;

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

    /**
     * 查询所有的字典数据 stats!=-1
     * @param map
     * @return
     */
    public List<WardDictionary> queryWardList(Map<String,Object> map){
        return wardDictionaryDao.queryWardList(map);
    }

    /**
     * 从his数据库视图中查询病区字典数据
     * @param map
     * @return list
     */
    public List<Map<String, Object>> getWardByHis(Map<String, Object> map){
        return patientTestDao.getWardByHis(map);
    }
}
