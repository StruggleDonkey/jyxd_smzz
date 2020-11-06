package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.CostDetailsDao;
import com.jyxd.web.data.basic.CostDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CostDetailsService {

    @Autowired
    private CostDetailsDao costDetailsDao;

    public boolean insert(CostDetails costDetails){
        return costDetailsDao.insert(costDetails);
    }

    public boolean update(CostDetails costDetails){
        return costDetailsDao.update(costDetails);
    }

    public CostDetails queryData(String id){
        return costDetailsDao.queryData(id);
    }

    public List<CostDetails> queryList(Map<String,Object> map){
        return costDetailsDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return costDetailsDao.queryNum(map);}

}
