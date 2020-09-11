package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BloodGasDao;
import com.jyxd.web.data.basic.BloodGas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BloodGasService {

    @Autowired
    private BloodGasDao bloodGasDao;

    public boolean insert(BloodGas bloodGas){
        return bloodGasDao.insert(bloodGas);
    }

    public boolean update(BloodGas bloodGas){
        return bloodGasDao.update(bloodGas);
    }

    public BloodGas queryData(String id){
        return bloodGasDao.queryData(id);
    }

    public List<BloodGas> queryList(Map<String,Object> map){
        return bloodGasDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return bloodGasDao.queryNum(map);}
}
