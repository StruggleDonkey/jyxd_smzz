package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.CatheterMaintainDao;
import com.jyxd.web.data.basic.CatheterMaintain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CatheterMaintainService {

    @Autowired
    private CatheterMaintainDao catheterMaintainDao;

    public boolean insert(CatheterMaintain catheterMaintain){
        return catheterMaintainDao.insert(catheterMaintain);
    }

    public boolean update(CatheterMaintain catheterMaintain){
        return catheterMaintainDao.update(catheterMaintain);
    }

    public CatheterMaintain queryData(String id){
        return catheterMaintainDao.queryData(id);
    }

    public List<CatheterMaintain> queryList(Map<String,Object> map){
        return catheterMaintainDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return catheterMaintainDao.queryNum(map);}
}
