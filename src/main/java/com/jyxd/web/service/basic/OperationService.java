package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.OperationDao;
import com.jyxd.web.data.basic.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OperationService {

    @Autowired
    private OperationDao operationDao;

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
}
