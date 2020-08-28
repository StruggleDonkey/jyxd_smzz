package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.FallForecastAssessmentDao;
import com.jyxd.web.data.assessment.FallForecastAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FallForecastAssessmentService {

    @Autowired
    private FallForecastAssessmentDao fallForecastAssessmentDao;

    public boolean insert(FallForecastAssessment fallForecastAssessment){
        return fallForecastAssessmentDao.insert(fallForecastAssessment);
    }

    public boolean update(FallForecastAssessment fallForecastAssessment){
        return fallForecastAssessmentDao.update(fallForecastAssessment);
    }

    public FallForecastAssessment queryData(String id){
        return fallForecastAssessmentDao.queryData(id);
    }

    public List<FallForecastAssessment> queryList(Map<String,Object> map){
        return fallForecastAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return fallForecastAssessmentDao.queryNum(map);}
}
