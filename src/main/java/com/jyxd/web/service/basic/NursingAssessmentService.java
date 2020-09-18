package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.NursingAssessmentDao;
import com.jyxd.web.data.basic.NursingAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NursingAssessmentService {

    @Autowired
    private NursingAssessmentDao nursingAssessmentDao;

    public boolean insert(NursingAssessment nursingAssessment){
        return nursingAssessmentDao.insert(nursingAssessment);
    }

    public boolean update(NursingAssessment nursingAssessment){
        return nursingAssessmentDao.update(nursingAssessment);
    }

    public NursingAssessment queryData(String id){
        return nursingAssessmentDao.queryData(id);
    }

    public List<NursingAssessment> queryList(Map<String,Object> map){
        return nursingAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return nursingAssessmentDao.queryNum(map);}
}
