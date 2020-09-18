package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.VitalSignDao;
import com.jyxd.web.data.basic.VitalSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VitalSignService {

    @Autowired
    private VitalSignDao vitalSignDao;

    public boolean insert(VitalSign vitalSign){
        return vitalSignDao.insert(vitalSign);
    }

    public boolean update(VitalSign vitalSign){
        return vitalSignDao.update(vitalSign);
    }

    public VitalSign queryData(String id){
        return vitalSignDao.queryData(id);
    }

    public List<VitalSign> queryList(Map<String,Object> map){
        return vitalSignDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return vitalSignDao.queryNum(map);}
}
