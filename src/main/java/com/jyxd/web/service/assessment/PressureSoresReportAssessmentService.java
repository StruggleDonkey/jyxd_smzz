package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.PressureSoresReportAssessmentDao;
import com.jyxd.web.data.assessment.PressureSoresReportAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PressureSoresReportAssessmentService {

    @Autowired
    private PressureSoresReportAssessmentDao pressureSoresReportAssessmentDao;

    public boolean insert(PressureSoresReportAssessment pressureSoresReportAssessment){
        return pressureSoresReportAssessmentDao.insert(pressureSoresReportAssessment);
    }

    public boolean update(PressureSoresReportAssessment pressureSoresReportAssessment){
        return pressureSoresReportAssessmentDao.update(pressureSoresReportAssessment);
    }

    public PressureSoresReportAssessment queryData(String id){
        return pressureSoresReportAssessmentDao.queryData(id);
    }

    public List<PressureSoresReportAssessment> queryList(Map<String,Object> map){
        return pressureSoresReportAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return pressureSoresReportAssessmentDao.queryNum(map);}
}
