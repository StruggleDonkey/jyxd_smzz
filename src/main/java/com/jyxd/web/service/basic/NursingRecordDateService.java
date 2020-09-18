package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.NursingRecordDateDao;
import com.jyxd.web.data.basic.NursingRecordDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NursingRecordDateService {

    @Autowired
    private NursingRecordDateDao nursingRecordDateDao;

    public boolean insert(NursingRecordDate nursingRecordDate){
        return nursingRecordDateDao.insert(nursingRecordDate);
    }

    public boolean update(NursingRecordDate nursingRecordDate){
        return nursingRecordDateDao.update(nursingRecordDate);
    }

    public NursingRecordDate queryData(String id){
        return nursingRecordDateDao.queryData(id);
    }

    public List<NursingRecordDate> queryList(Map<String,Object> map){
        return nursingRecordDateDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return nursingRecordDateDao.queryNum(map);}
}
