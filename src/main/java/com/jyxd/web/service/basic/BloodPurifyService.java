package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BloodPurifyDao;
import com.jyxd.web.data.basic.BloodPurify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BloodPurifyService {

    @Autowired
    private BloodPurifyDao bloodPurifyDao;

    public boolean insert(BloodPurify bloodPurify){
        return bloodPurifyDao.insert(bloodPurify);
    }

    public boolean update(BloodPurify bloodPurify){
        return bloodPurifyDao.update(bloodPurify);
    }

    public BloodPurify queryData(String id){
        return bloodPurifyDao.queryData(id);
    }

    public List<BloodPurify> queryList(Map<String,Object> map){
        return bloodPurifyDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return bloodPurifyDao.queryNum(map);}

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return bloodPurifyDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return bloodPurifyDao.getNum(map);}

    /**
     * 根据maintenanceId查询对象列表
     * @param maintenanceId
     * @return
     */
    public List<BloodPurify> queryListByMaintenanceId(String maintenanceId){
        return bloodPurifyDao.queryListByMaintenanceId(maintenanceId);
    }

    /**
     * 根据根据maintenanceId查询对象
     * @param map
     * @return
     */
    public Map<String,Object> getDataByMaintenanceId(Map<String, Object> map){
        return bloodPurifyDao.getDataByMaintenanceId(map);
    }

}
