package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.NursingPlanDao;
import com.jyxd.web.data.basic.NursingPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NursingPlanService {

    @Autowired
    private NursingPlanDao nursingPlanDao;

    public boolean insert(NursingPlan nursingPlan){
        return nursingPlanDao.insert(nursingPlan);
    }

    public boolean update(NursingPlan nursingPlan){
        return nursingPlanDao.update(nursingPlan);
    }

    public NursingPlan queryData(String id){
        return nursingPlanDao.queryData(id);
    }

    public List<NursingPlan> queryList(Map<String,Object> map){
        return nursingPlanDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return nursingPlanDao.queryNum(map);}

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return nursingPlanDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return nursingPlanDao.getNum(map);}

    /**
     * 根据 assessmentId 查询重症护理计划表记录列表
     * @param assessmentId
     * @return list
     */
    public List<NursingPlan> queryListByAssessmentId(String assessmentId){
        return nursingPlanDao.queryListByAssessmentId(assessmentId);
    }

    /**
     * 根据 assessmentId 查询重症护理计划对象
     * @param map
     * @return
     */
    public Map<String, Object> queryDataByAssessmentId(Map<String, Object> map){
        return nursingPlanDao.queryDataByAssessmentId(map);
    }
}
