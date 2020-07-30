package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.MonitorDictionaryDao;
import com.jyxd.web.data.dictionary.MonitorDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MonitorDictionaryService {

    @Autowired
    private MonitorDictionaryDao monitorDictionaryDao;

    public boolean insert(MonitorDictionary monitorDictionary){
        return monitorDictionaryDao.insert(monitorDictionary);
    }

    public boolean update(MonitorDictionary monitorDictionary){
        return monitorDictionaryDao.update(monitorDictionary);
    }

    public MonitorDictionary queryData(String id){
        return monitorDictionaryDao.queryData(id);
    }

    public List<MonitorDictionary> queryList(Map<String,Object> map){
        return monitorDictionaryDao.queryList(map);
    }
}
