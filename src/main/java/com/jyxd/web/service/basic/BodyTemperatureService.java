package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BodyTemperatureDao;
import com.jyxd.web.data.basic.BodyTemperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BodyTemperatureService {

    @Autowired
    private BodyTemperatureDao bodyTemperatureDao;

    public boolean insert(BodyTemperature bodyTemperature){
        return bodyTemperatureDao.insert(bodyTemperature);
    }

    public boolean update(BodyTemperature bodyTemperature){
        return bodyTemperatureDao.update(bodyTemperature);
    }

    public BodyTemperature queryData(String id){
        return bodyTemperatureDao.queryData(id);
    }

    public List<BodyTemperature> queryList(Map<String,Object> map){
        return bodyTemperatureDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return bodyTemperatureDao.queryNum(map);}
}
