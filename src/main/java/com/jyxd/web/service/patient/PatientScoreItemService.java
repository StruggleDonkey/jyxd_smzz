package com.jyxd.web.service.patient;

import com.jyxd.web.dao.patient.PatientScoreItemDao;
import com.jyxd.web.data.patient.PatientScoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientScoreItemService {

    @Autowired
    private PatientScoreItemDao patientScoreItemDao;

    public boolean insert(PatientScoreItem patientScoreItem){
        return patientScoreItemDao.insert(patientScoreItem);
    }

    public boolean update(PatientScoreItem patientScoreItem){
        return patientScoreItemDao.update(patientScoreItem);
    }

    public PatientScoreItem queryData(String id){
        return patientScoreItemDao.queryData(id);
    }

    public List<PatientScoreItem> queryList(Map<String,Object> map){
        return patientScoreItemDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return patientScoreItemDao.queryNum(map);}

    /**
     * 根据条件查询病人评分明细对象 评分时间 评分项 评分明细 病人主键id 病人评分主键id
     * @param map
     * @return
     */
    public PatientScoreItem queryDataByTypeAndTime(Map<String,Object> map){
        return patientScoreItemDao.queryDataByTypeAndTime(map);
    }

    /**
     * 根据病人评分主键id 查询病人评分明细中 评分明细主键id 集合
     * @param patientScoreId
     * @return
     */
    public List<String> getItemIdByPatientScoreId(String patientScoreId){
        return patientScoreItemDao.getItemIdByPatientScoreId(patientScoreId);
    }

    /**
     * 根据病人评分主键id 查询病人评分明细列表
     * @param map
     * @return
     */
    public List<PatientScoreItem> queryListByPatientScoreId(Map<String,Object> map){
        return patientScoreItemDao.queryListByPatientScoreId(map);
    }

    /**
     * 删除对象
     * @param
     * @return
     */
    public boolean deleteData(String id){
        return patientScoreItemDao.deleteData(id);
    }
}
