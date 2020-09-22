package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.VeinPortMaintainDao;
import com.jyxd.web.data.basic.VeinPortMaintain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VeinPortMaintainService {

    @Autowired
    private VeinPortMaintainDao veinPortMaintainDao;

    public boolean insert(VeinPortMaintain veinPortMaintain){
        return veinPortMaintainDao.insert(veinPortMaintain);
    }

    public boolean update(VeinPortMaintain veinPortMaintain){
        return veinPortMaintainDao.update(veinPortMaintain);
    }

    public VeinPortMaintain queryData(String id){
        return veinPortMaintainDao.queryData(id);
    }

    public List<VeinPortMaintain> queryList(Map<String,Object> map){
        return veinPortMaintainDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return veinPortMaintainDao.queryNum(map);}
}
