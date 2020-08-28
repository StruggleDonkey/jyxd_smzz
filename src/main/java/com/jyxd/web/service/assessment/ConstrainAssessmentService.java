package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.ConstrainAssessmentDao;
import com.jyxd.web.data.assessment.ConstrainAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ConstrainAssessmentService {

    @Autowired
    private ConstrainAssessmentDao constrainAssessmentDao;

    public boolean insert(ConstrainAssessment constrainAssessment){
        return constrainAssessmentDao.insert(constrainAssessment);
    }

    public boolean update(ConstrainAssessment constrainAssessment){
        return constrainAssessmentDao.update(constrainAssessment);
    }

    public ConstrainAssessment queryData(String id){
        return constrainAssessmentDao.queryData(id);
    }

    public List<ConstrainAssessment> queryList(Map<String,Object> map){
        return constrainAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return constrainAssessmentDao.queryNum(map);}
}
