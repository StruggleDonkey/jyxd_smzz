package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.OperationDao;
import com.jyxd.web.data.basic.Operation;
import com.jyxd.web.mapper.PatientTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("operationService")
@Transactional
public class OperationService {

    @Autowired
    private OperationDao operationDao;

    @Autowired
    private PatientTestDao patientTestDao;


    public boolean insert(Operation operation){
        return operationDao.insert(operation);
    }

    public boolean update(Operation operation){
        return operationDao.update(operation);
    }

    public Operation queryData(String id){
        return operationDao.queryData(id);
    }

    public List<Operation> queryList(Map<String,Object> map){
        return operationDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return operationDao.queryNum(map);}

    /**
     * 查询所有手术信息 status!=-1
     * @param map
     * @return
     */
    public List<Operation> queryOperationList(Map<String,Object> map){
        return operationDao.queryOperationList(map);
    }

    /**
     * 从his系统视图查询所有手术信息
     * @return
     */
    public List<Map<String, Object>> getOperationByHis(Map<String,Object> map){
        return patientTestDao.getOperationByHis(map);
    }
}
