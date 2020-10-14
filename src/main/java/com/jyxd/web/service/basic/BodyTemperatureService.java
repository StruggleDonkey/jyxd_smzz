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

    /**
     * 根据时间查询在科病人的体温图数据
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByTime(Map<String,Object> map){
        return bodyTemperatureDao.getListByTime(map);
    }

    /**
     * 根据时间和code查询体温单对象
     * @param map
     * @return
     */
    public BodyTemperature queryDataByTimeAndCode(Map<String,Object> map){
        return bodyTemperatureDao.queryDataByTimeAndCode(map);
    }

}
