package com.jyxd.web.service.patientService;

import com.jyxd.web.dao.patientDao.PatientLogDao;
import com.jyxd.web.data.patient.PatientLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientLogService {

    @Autowired
    private PatientLogDao patientLogDao;

    public boolean insert(PatientLog patientLog){
        return patientLogDao.insert(patientLog);
    }

    public boolean update(PatientLog patientLog){
        return patientLogDao.update(patientLog);
    }

    public PatientLog queryData(String id){
        return patientLogDao.queryData(id);
    }

    public List<PatientLog> queryList(Map<String,Object> map){
        return patientLogDao.queryList(map);
    }
}
