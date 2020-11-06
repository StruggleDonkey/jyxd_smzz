package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BloodPurifyRecordDao;
import com.jyxd.web.data.basic.BloodPurifyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BloodPurifyRecordService {

    @Autowired
    private BloodPurifyRecordDao bloodPurifyRecordDao;

    public boolean insert(BloodPurifyRecord bloodPurifyRecord){
        return bloodPurifyRecordDao.insert(bloodPurifyRecord);
    }

    public boolean update(BloodPurifyRecord bloodPurifyRecord){
        return bloodPurifyRecordDao.update(bloodPurifyRecord);
    }

    public BloodPurifyRecord queryData(String id){
        return bloodPurifyRecordDao.queryData(id);
    }

    public List<BloodPurifyRecord> queryList(Map<String,Object> map){
        return bloodPurifyRecordDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return bloodPurifyRecordDao.queryNum(map);}

}
