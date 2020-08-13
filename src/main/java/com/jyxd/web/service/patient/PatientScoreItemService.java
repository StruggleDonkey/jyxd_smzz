package com.jyxd.web.service.patient;

import com.jyxd.web.dao.patient.PatientScoreItemDao;
import com.jyxd.web.data.patient.PatientScoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientScoreItemService {

    @Autowired
    private PatientScoreItemDao patientScoreItemDao;

    public boolean insert(PatientScoreItem patientScoreItem){
        return patientScoreItemDao.insert(patientScoreItem);
    }

    public boolean update(PatientScoreItem patientScoreItem){
        return patientScoreItemDao.update(patientScoreItem);
    }

    public PatientScoreItem queryData(String id){
        return patientScoreItemDao.queryData(id);
    }

    public List<PatientScoreItem> queryList(Map<String,Object> map){
        return patientScoreItemDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return patientScoreItemDao.queryNum(map);}
}
