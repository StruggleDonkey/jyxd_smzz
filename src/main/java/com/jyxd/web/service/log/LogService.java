package com.jyxd.web.service.log;

import com.jyxd.web.dao.log.LogDao;
import com.jyxd.web.data.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("logService")
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

    public int queryNum(Map<String,Object> map){return logDao.queryNum(map);}

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return logDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return logDao.getNum(map);}

}
