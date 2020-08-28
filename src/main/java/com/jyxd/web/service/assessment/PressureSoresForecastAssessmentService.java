package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.PressureSoresForecastAssessmentDao;
import com.jyxd.web.data.assessment.PressureSoresForecastAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PressureSoresForecastAssessmentService {

    @Autowired
    private PressureSoresForecastAssessmentDao pressureSoresForecastAssessmentDao;

    public boolean insert(PressureSoresForecastAssessment pressureSoresForecastAssessment){
        return pressureSoresForecastAssessmentDao.insert(pressureSoresForecastAssessment);
    }

    public boolean update(PressureSoresForecastAssessment pressureSoresForecastAssessment){
        return pressureSoresForecastAssessmentDao.update(pressureSoresForecastAssessment);
    }

    public PressureSoresForecastAssessment queryData(String id){
        return pressureSoresForecastAssessmentDao.queryData(id);
    }

    public List<PressureSoresForecastAssessment> queryList(Map<String,Object> map){
        return pressureSoresForecastAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return pressureSoresForecastAssessmentDao.queryNum(map);}
}
