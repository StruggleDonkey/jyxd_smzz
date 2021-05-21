package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.MonitorDataDao;
import com.jyxd.web.data.basic.MonitorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MonitorDataService {

    @Autowired
    private MonitorDataDao monitorDataDao;

    public boolean insert(MonitorData monitorData){
        return monitorDataDao.insert(monitorData);
    }

    public boolean update(MonitorData monitorData){
        return monitorDataDao.update(monitorData);
    }

    public MonitorData queryData(String id){
        return monitorDataDao.queryData(id);
    }

    public List<MonitorData> queryList(Map<String,Object> map){
        return monitorDataDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return monitorDataDao.queryNum(map);}
}
