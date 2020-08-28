package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.TransferAssessmentDao;
import com.jyxd.web.data.assessment.TransferAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransferAssessmentService {

    @Autowired
    private TransferAssessmentDao transferAssessmentDao;

    public boolean insert(TransferAssessment transferAssessment){
        return transferAssessmentDao.insert(transferAssessment);
    }

    public boolean update(TransferAssessment transferAssessment){
        return transferAssessmentDao.update(transferAssessment);
    }

    public TransferAssessment queryData(String id){
        return transferAssessmentDao.queryData(id);
    }

    public List<TransferAssessment> queryList(Map<String,Object> map){
        return transferAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return transferAssessmentDao.queryNum(map);}
}
