package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.HospitalizedAssessmentDao;
import com.jyxd.web.data.assessment.HospitalizedAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HospitalizedAssessmentService {

    @Autowired
    private HospitalizedAssessmentDao hospitalizedAssessmentDao;

    public boolean insert(HospitalizedAssessment hospitalizedAssessment){
        return hospitalizedAssessmentDao.insert(hospitalizedAssessment);
    }

    public boolean update(HospitalizedAssessment hospitalizedAssessment){
        return hospitalizedAssessmentDao.update(hospitalizedAssessment);
    }

    public HospitalizedAssessment queryData(String id){
        return hospitalizedAssessmentDao.queryData(id);
    }

    public List<HospitalizedAssessment> queryList(Map<String,Object> map){
        return hospitalizedAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return hospitalizedAssessmentDao.queryNum(map);}
}
