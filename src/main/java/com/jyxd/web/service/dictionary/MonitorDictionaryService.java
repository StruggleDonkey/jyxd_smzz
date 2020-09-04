package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.MonitorDictionaryDao;
import com.jyxd.web.data.dictionary.MonitorDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
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

    public int queryNum(Map<String,Object> map){return monitorDictionaryDao.queryNum(map);}

    /**
     * 床位安排--查询未分配的监护仪
     * @param map
     * @return
     */
    public List<MonitorDictionary> getNoBedMonitorList(Map<String,Object> map){
        return monitorDictionaryDao.getNoBedMonitorList(map);
    }
}
