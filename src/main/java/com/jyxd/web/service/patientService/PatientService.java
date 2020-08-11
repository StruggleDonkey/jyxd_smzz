package com.jyxd.web.service.patientService;

import com.jyxd.web.dao.patientDao.PatientDao;
import com.jyxd.web.data.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientDao patientDao;

    public boolean insert(Patient patient){
        return patientDao.insert(patient);
    }

    public boolean update(Patient patient){
        return patientDao.update(patient);
    }

    public Patient queryData(String id){
        return patientDao.queryData(id);
    }

    public List<Patient> queryList(Map<String,Object> map){
        return patientDao.queryList(map);
    }
}
