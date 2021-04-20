package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.MedOrderExecDao;
import com.jyxd.web.data.basic.MedOrderExec;
import com.jyxd.web.mapper.PatientTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("medOrderExecService")
@Transactional
public class MedOrderExecService {

    @Autowired
    private MedOrderExecDao medOrderExecDao;

    @Autowired
    private PatientTestDao patientTestDao;

    public boolean insert(MedOrderExec medOrderExec){
        return medOrderExecDao.insert(medOrderExec);
    }

    public boolean update(MedOrderExec medOrderExec){
        return medOrderExecDao.update(medOrderExec);
    }

    public MedOrderExec queryData(String id){
        return medOrderExecDao.queryData(id);
    }

    public List<MedOrderExec> queryList(Map<String,Object> map){
        return medOrderExecDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return medOrderExecDao.queryNum(map);}

    /**
     * 查询所有医嘱对象列表
     * @param map
     * @return
     */
    public  List<MedOrderExec> queryMedOrderExecList(Map<String,Object> map){
        return medOrderExecDao.queryMedOrderExecList(map);
    }

    /**
     * 从his系统视图查询所有医嘱执行信息
     * @return
     */
    public List<Map<String, Object>> getMedOrderExecByHis(Map<String,Object> map){
        return patientTestDao.getMedOrderExecByHis(map);
    }

    public List<Map<String, Object>> getList(Map<String,Object> map){
        return medOrderExecDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return medOrderExecDao.getNum(map);}
}
