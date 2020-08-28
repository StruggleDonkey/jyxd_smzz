package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.IntoAssessmentDao;
import com.jyxd.web.data.assessment.IntoAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IntoAssessmentService {

    @Autowired
    private IntoAssessmentDao intoAssessmentDao;

    public boolean insert(IntoAssessment intoAssessment){
        return intoAssessmentDao.insert(intoAssessment);
    }

    public boolean update(IntoAssessment intoAssessment){
        return intoAssessmentDao.update(intoAssessment);
    }

    public IntoAssessment queryData(String id){
        return intoAssessmentDao.queryData(id);
    }

    public List<IntoAssessment> queryList(Map<String,Object> map){
        return intoAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return intoAssessmentDao.queryNum(map);}
}
