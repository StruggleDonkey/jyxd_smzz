package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.MonitorDao;
import com.jyxd.web.data.basic.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MonitorService {

    @Autowired
    private MonitorDao monitorDao;

    public boolean insert(Monitor monitor){
        return monitorDao.insert(monitor);
    }

    public boolean update(Monitor monitor){
        return monitorDao.update(monitor);
    }

    public Monitor queryData(String id){
        return monitorDao.queryData(id);
    }

    public List<Monitor> queryList(Map<String,Object> map){
        return monitorDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return monitorDao.queryNum(map);}
}
