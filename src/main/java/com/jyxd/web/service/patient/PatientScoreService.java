package com.jyxd.web.service.patient;

import com.jyxd.web.dao.patient.PatientScoreDao;
import com.jyxd.web.data.patient.PatientScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientScoreService {

    @Autowired
    private PatientScoreDao patientScoreDao;

    public boolean insert(PatientScore patientScore){
        return patientScoreDao.insert(patientScore);
    }

    public boolean update(PatientScore patientScore){
        return patientScoreDao.update(patientScore);
    }

    public PatientScore queryData(String id){
        return patientScoreDao.queryData(id);
    }

    public List<PatientScore> queryList(Map<String,Object> map){
        return patientScoreDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return patientScoreDao.queryNum(map);}
}
