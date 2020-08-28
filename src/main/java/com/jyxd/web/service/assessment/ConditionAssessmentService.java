package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.ConditionAssessmentDao;
import com.jyxd.web.data.assessment.ConditionAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ConditionAssessmentService {

    @Autowired
    private ConditionAssessmentDao conditionAssessmentDao;

    public boolean insert(ConditionAssessment conditionAssessment){
        return conditionAssessmentDao.insert(conditionAssessment);
    }

    public boolean update(ConditionAssessment conditionAssessment){
        return conditionAssessmentDao.update(conditionAssessment);
    }

    public ConditionAssessment queryData(String id){
        return conditionAssessmentDao.queryData(id);
    }

    public List<ConditionAssessment> queryList(Map<String,Object> map){
        return conditionAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return conditionAssessmentDao.queryNum(map);}
}
