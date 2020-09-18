package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.MedOrderExecDao;
import com.jyxd.web.data.basic.MedOrderExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MedOrderExecService {

    @Autowired
    private MedOrderExecDao medOrderExecDao;

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
}
