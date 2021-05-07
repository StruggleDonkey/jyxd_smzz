package com.jyxd.web.service.patient;

import com.jyxd.web.dao.patient.PatientScoreDao;
import com.jyxd.web.dao.patient.PatientScoreItemDao;
import com.jyxd.web.data.patient.PatientScore;
import com.jyxd.web.data.patient.PatientScoreItem;
import com.jyxd.web.data.user.User;
import com.jyxd.web.util.UUIDUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientScoreItemService {

    @Autowired
    private PatientScoreItemDao patientScoreItemDao;
    @Autowired
    private PatientScoreDao patientScoreDao;

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

    /**
     * 新增一条评分记录
     * @param map
     * @param user
     * @throws ParseException
     */
    public void insertPatientScore(Map<String,Object> map,User user) throws ParseException {
        PatientScore patientScore=new PatientScore();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        patientScore.setId(UUIDUtil.getUUID());
        patientScore.setCreateTime(new Date());
        patientScore.setScoreTime(format.parse(map.get("scoreTime").toString()));
        patientScore.setScore((int)map.get("score"));
        patientScore.setStatus(1);
       // User user=(User) session.getAttribute("user");
        if(user!=null){
            patientScore.setOperatorCode(user.getLoginName());
        }
        patientScore.setSignature(map.get("signature").toString());
        patientScore.setVisitId(map.get("visitId").toString());
        patientScore.setVisitCode(map.get("visitCode").toString());
        patientScore.setType(map.get("type").toString());
        patientScore.setPatientId(map.get("patientId").toString());
        if(StringUtils.isNotEmpty(map.get("reportTime").toString())){
            patientScore.setReportTime(format.parse(map.get("reportTime").toString()));
        }
        if(StringUtils.isNotEmpty(map.get("assessmentTime").toString())){
            patientScore.setAssessmentTime(format.parse(map.get("assessmentTime").toString()));
        }
        if(StringUtils.isNotEmpty(map.get("nursingStep").toString())){
            patientScore.setNursingStep(map.get("nursingStep").toString());
        }
        if(StringUtils.isNotEmpty(map.get("otherStep").toString())){
            patientScore.setOtherStep(map.get("otherStep").toString());
        }
        if(StringUtils.isNotEmpty(map.get("mortalityRate").toString())){
            patientScore.setMortalityRate(map.get("mortalityRate").toString());
        }
        if(StringUtils.isNotEmpty(map.get("extendColumn").toString())){
            patientScore.setExtendColumn(map.get("extendColumn").toString());
        }
        if(StringUtils.isNotEmpty(map.get("scoreKnowledgeId").toString())){
            patientScore.setScoreKnowledgeId(map.get("scoreKnowledgeId").toString());
        }
        patientScoreDao.insert(patientScore);
        //新增病人评分明细记录
        if(map.get("list")!=null){
            JSONArray array=JSONArray.fromObject(map.get("list").toString());
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonObject=(JSONObject) array.get(i);
                PatientScoreItem patientScoreItem=new PatientScoreItem();
                patientScoreItem.setId(UUIDUtil.getUUID());
                patientScoreItem.setCreateTime(new Date());
                patientScoreItem.setVisitId(map.get("visitId").toString());
                patientScoreItem.setVisitCode(map.get("visitCode").toString());
                patientScoreItem.setType(map.get("type").toString());
                patientScoreItem.setPatientId(map.get("patientId").toString());
                patientScoreItem.setScoreTime(format.parse(map.get("scoreTime").toString()));
                patientScoreItem.setPatientScoreId(patientScore.getId());
                patientScoreItem.setParentId(jsonObject.getString("parentId"));
                patientScoreItem.setItemId(jsonObject.getString("itemId"));
                patientScoreItem.setContent(jsonObject.getString("content"));
                patientScoreItem.setExtraContent(jsonObject.getString("extraContent"));
                insert(patientScoreItem);
            }
        }
    }
}
