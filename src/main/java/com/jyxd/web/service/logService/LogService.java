package com.jyxd.web.service.logService;

import com.jyxd.web.dao.logDao.LogDao;
import com.jyxd.web.data.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogService {

    @Autowired
    private LogDao logDao;

    public boolean insert(Log log){
        return logDao.insert(log);
    }

    public boolean update(Log log){
        return logDao.update(log);
    }

    public Log queryData(String id){
        return logDao.queryData(id);
    }

    public List<Log> queryList(Map<String,Object> map){
        return logDao.queryList(map);
    }

}
