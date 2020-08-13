package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.TransfusionAssessmentDao;
import com.jyxd.web.data.assessment.TransfusionAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransfusionAssessmentService {

    @Autowired
    private TransfusionAssessmentDao transfusionAssessmentDao;

    public boolean insert(TransfusionAssessment transfusionAssessment){
        return transfusionAssessmentDao.insert(transfusionAssessment);
    }

    public boolean update(TransfusionAssessment transfusionAssessment){
        return transfusionAssessmentDao.update(transfusionAssessment);
    }

    public TransfusionAssessment queryData(String id){
        return transfusionAssessmentDao.queryData(id);
    }

    public List<TransfusionAssessment> queryList(Map<String,Object> map){
        return transfusionAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return transfusionAssessmentDao.queryNum(map);}
}
