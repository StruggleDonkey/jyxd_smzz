package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.MedOrderExecSyncDao;
import com.jyxd.web.data.basic.MedOrderExecSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MedOrderExecSyncService {

    @Autowired
    private MedOrderExecSyncDao medOrderExecSyncDao;

    public boolean insert(MedOrderExecSync medOrderExecSync){
        return medOrderExecSyncDao.insert(medOrderExecSync);
    }

    public boolean update(MedOrderExecSync medOrderExecSync){
        return medOrderExecSyncDao.update(medOrderExecSync);
    }

    public MedOrderExecSync queryData(String id){
        return medOrderExecSyncDao.queryData(id);
    }

    public List<MedOrderExecSync> queryList(Map<String,Object> map){
        return medOrderExecSyncDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return medOrderExecSyncDao.queryNum(map);}

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return medOrderExecSyncDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return medOrderExecSyncDao.getNum(map);}
}
