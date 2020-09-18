package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.VitalSignAlarmDao;
import com.jyxd.web.data.basic.VitalSignAlarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VitalSignAlarmService {

    @Autowired
    private VitalSignAlarmDao vitalSignAlarmDao;

    public boolean insert(VitalSignAlarm vitalSignAlarm){
        return vitalSignAlarmDao.insert(vitalSignAlarm);
    }

    public boolean update(VitalSignAlarm vitalSignAlarm){
        return vitalSignAlarmDao.update(vitalSignAlarm);
    }

    public VitalSignAlarm queryData(String id){
        return vitalSignAlarmDao.queryData(id);
    }

    public List<VitalSignAlarm> queryList(Map<String,Object> map){
        return vitalSignAlarmDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return vitalSignAlarmDao.queryNum(map);}
}
