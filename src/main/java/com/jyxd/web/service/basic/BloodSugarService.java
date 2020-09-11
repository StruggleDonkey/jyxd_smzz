package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BloodSugarDao;
import com.jyxd.web.data.basic.BloodSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BloodSugarService {

    @Autowired
    private BloodSugarDao bloodSugarDao;

    public boolean insert(BloodSugar bloodSugar){
        return bloodSugarDao.insert(bloodSugar);
    }

    public boolean update(BloodSugar bloodSugar){
        return bloodSugarDao.update(bloodSugar);
    }

    public BloodSugar queryData(String id){
        return bloodSugarDao.queryData(id);
    }

    public List<BloodSugar> queryList(Map<String,Object> map){
        return bloodSugarDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return bloodSugarDao.queryNum(map);}
}
