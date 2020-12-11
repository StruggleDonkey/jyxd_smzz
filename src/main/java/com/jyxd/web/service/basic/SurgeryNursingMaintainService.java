package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.SurgeryNursingMaintainDao;
import com.jyxd.web.data.basic.SurgeryNursingMaintain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SurgeryNursingMaintainService {

    @Autowired
    private SurgeryNursingMaintainDao surgeryNursingMaintainDao;

    public boolean insert(SurgeryNursingMaintain surgeryNursingMaintain){
        return surgeryNursingMaintainDao.insert(surgeryNursingMaintain);
    }

    public boolean update(SurgeryNursingMaintain surgeryNursingMaintain){
        return surgeryNursingMaintainDao.update(surgeryNursingMaintain);
    }

    public SurgeryNursingMaintain queryData(String id){
        return surgeryNursingMaintainDao.queryData(id);
    }

    public List<SurgeryNursingMaintain> queryList(Map<String,Object> map){
        return surgeryNursingMaintainDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return surgeryNursingMaintainDao.queryNum(map);}

    /**
     * 根据条件查询一条围手术期护理记录表记录
     * @param map
     * @return SurgeryNursingMaintain
     */
    public SurgeryNursingMaintain getData(Map<String, Object> map){
        return surgeryNursingMaintainDao.getData(map);
    }

    /**
     * 根据 maintenanceId 查询 maintenanceRecordId
     * @param maintenanceId
     * @return
     */
    public String getMaintenanceRecordId(String maintenanceId){
        return surgeryNursingMaintainDao.getMaintenanceRecordId(maintenanceId);
    }

    /**
     * 根据 病人主键id 查询 围手术期护理记录列表
     * @param map
     * @return
     */
    public List<Map<String, Object>> getList(Map<String, Object> map){
        return surgeryNursingMaintainDao.getList(map);
    }

    /**
     * 根据 maintenanceRecordId 查询 围手术期护理记录列表
     * @param maintenanceRecordId
     * @return
     */
    public List<SurgeryNursingMaintain> queryListByMaintenanceRecordId(String maintenanceRecordId){
        return surgeryNursingMaintainDao.queryListByMaintenanceRecordId(maintenanceRecordId);
    }

}
