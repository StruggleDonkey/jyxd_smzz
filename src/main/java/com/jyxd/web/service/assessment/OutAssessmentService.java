package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.OutAssessmentDao;
import com.jyxd.web.data.assessment.OutAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OutAssessmentService {

    @Autowired
    private OutAssessmentDao outAssessmentDao;

    public boolean insert(OutAssessment outAssessment){
        return outAssessmentDao.insert(outAssessment);
    }

    public boolean update(OutAssessment outAssessment){
        return outAssessmentDao.update(outAssessment);
    }

    public OutAssessment queryData(String id){
        return outAssessmentDao.queryData(id);
    }

    public List<OutAssessment> queryList(Map<String,Object> map){
        return outAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return outAssessmentDao.queryNum(map);}
}
