package com.jyxd.web.service.logService;

import com.jyxd.web.dao.logDao.IcuLogDao;
import com.jyxd.web.data.log.IcuLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IcuLogService {

    @Autowired
    private IcuLogDao icuLogDao;

    public boolean insert(IcuLog icuLog){
        return icuLogDao.insert(icuLog);
    }

    public boolean update(IcuLog icuLog){
        return icuLogDao.update(icuLog);
    }

    public IcuLog queryData(String id){
        return icuLogDao.queryData(id);
    }

    public List<IcuLog> queryList(Map<String,Object> map){
        return icuLogDao.queryList(map);
    }

}
