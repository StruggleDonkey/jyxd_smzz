package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.VeinPortDao;
import com.jyxd.web.data.basic.VeinPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VeinPortService {

    @Autowired
    private VeinPortDao veinPortDao;

    public boolean insert(VeinPort veinPort){
        return veinPortDao.insert(veinPort);
    }

    public boolean update(VeinPort veinPort){
        return veinPortDao.update(veinPort);
    }

    public VeinPort queryData(String id){
        return veinPortDao.queryData(id);
    }

    public List<VeinPort> queryList(Map<String,Object> map){
        return veinPortDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return veinPortDao.queryNum(map);}
}
