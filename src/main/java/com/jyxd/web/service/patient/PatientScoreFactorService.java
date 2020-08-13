package com.jyxd.web.service.patient;

import com.jyxd.web.dao.patient.PatientScoreFactorDao;
import com.jyxd.web.data.patient.PatientScoreFactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientScoreFactorService {

    @Autowired
    private PatientScoreFactorDao patientScoreFactorDao;

    public boolean insert(PatientScoreFactor patientScoreFactor){
        return patientScoreFactorDao.insert(patientScoreFactor);
    }

    public boolean update(PatientScoreFactor patientScoreFactor){
        return patientScoreFactorDao.update(patientScoreFactor);
    }

    public PatientScoreFactor queryData(String id){
        return patientScoreFactorDao.queryData(id);
    }

    public List<PatientScoreFactor> queryList(Map<String,Object> map){
        return patientScoreFactorDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return patientScoreFactorDao.queryNum(map);}
}
