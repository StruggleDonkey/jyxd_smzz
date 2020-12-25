package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.HospitalizedAssessmentDao;
import com.jyxd.web.data.assessment.HospitalizedAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HospitalizedAssessmentService {

    @Autowired
    private HospitalizedAssessmentDao hospitalizedAssessmentDao;

    public boolean insert(HospitalizedAssessment hospitalizedAssessment){
        return hospitalizedAssessmentDao.insert(hospitalizedAssessment);
    }

    public boolean update(HospitalizedAssessment hospitalizedAssessment){
        return hospitalizedAssessmentDao.update(hospitalizedAssessment);
    }

    public HospitalizedAssessment queryData(String id){
        return hospitalizedAssessmentDao.queryData(id);
    }

    public List<HospitalizedAssessment> queryList(Map<String,Object> map){
        return hospitalizedAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return hospitalizedAssessmentDao.queryNum(map);}

    /**
     * 根据记录时间和病人id查询对象列表
     * @return
     */
    public List<HospitalizedAssessment> getListByDataTimeAndPatientId(Map<String,Object> map){
        return hospitalizedAssessmentDao.getListByDataTimeAndPatientId(map);
    }

    /**
     * 根据评估主键和病人id查询对象列表
     * @return
     */
    public List<HospitalizedAssessment> getListByAssessmentIdAndPatientId(Map<String,Object> map){
        return hospitalizedAssessmentDao.getListByAssessmentIdAndPatientId(map);
    }

    /**
     * 护理评估--入院护理评估单--历史列表--分页查询列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getList(Map<String,Object> map){
        return hospitalizedAssessmentDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return hospitalizedAssessmentDao.getNum(map);}

    /**
     * 根据评估主键查询对象
     * @param map
     * @return
     */
    public Map<String,Object> getDataByAssessmentId(Map<String,Object> map){
        return hospitalizedAssessmentDao.getDataByAssessmentId(map);
    }
}
