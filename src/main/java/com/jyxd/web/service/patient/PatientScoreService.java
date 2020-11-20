package com.jyxd.web.service.patient;

import com.jyxd.web.dao.patient.PatientScoreDao;
import com.jyxd.web.data.patient.PatientScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientScoreService {

    @Autowired
    private PatientScoreDao patientScoreDao;

    public boolean insert(PatientScore patientScore){
        return patientScoreDao.insert(patientScore);
    }

    public boolean update(PatientScore patientScore){
        return patientScoreDao.update(patientScore);
    }

    public PatientScore queryData(String id){
        return patientScoreDao.queryData(id);
    }

    public List<PatientScore> queryList(Map<String,Object> map){
        return patientScoreDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return patientScoreDao.queryNum(map);}

    /**
     * 根据评分类型 分数 病人主键id 查询评分记录列表
     * @param map
     * @return
     */
    public List<PatientScore> queryDataByScoreAndType(Map<String,Object> map){
        return patientScoreDao.queryDataByScoreAndType(map);
    }

    /**
     * 根据评分类型 病人主键id 查询评分记录列表 （group by 评分时间）
     * @param map
     * @return
     */
    public List<PatientScore> queryDataListGroupByTime(Map<String,Object> map){
        return patientScoreDao.queryDataListGroupByTime(map);
    }

    /**
     * 根据评分时间 评分类型 病人主键id 查询 病人评分列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getPatientScoreList(Map<String,Object> map){
        return patientScoreDao.getPatientScoreList(map);
    }

    /**
     * 根据主键id 获取评分时间  带准确时分秒的
     * @param id
     * @return
     */
    public String getTimeById(String id){
        return patientScoreDao.getTimeById(id);
    }

    /**
     * 重症评分--跌倒坠床--复制评分--查询病人该项评分列表
     * @param map patientId type
     * @return
     */
    public List<Map<String,Object>>  getListByPatientIdAndType(Map<String,Object> map){
        return patientScoreDao.getListByPatientIdAndType(map);
    }

}
