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
}
