package com.jyxd.web.service.patient;

import com.jyxd.web.dao.patient.PatientDao;
import com.jyxd.web.data.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
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

    public int queryNum(Map<String,Object> map){return patientDao.queryNum(map);}

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return patientDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return patientDao.getNum(map);}

    public int getAllNum(){return patientDao.getAllNum();}

    public List<LinkedHashMap<String,Object>> getDownloadList(Map<String,Object> map){
        return patientDao.getDownloadList(map);
    }

}
