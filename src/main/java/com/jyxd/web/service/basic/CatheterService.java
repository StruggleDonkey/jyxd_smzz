package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.CatheterDao;
import com.jyxd.web.data.basic.Catheter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CatheterService {

    @Autowired
    private CatheterDao catheterDao;

    public boolean insert(Catheter catheter){
        return catheterDao.insert(catheter);
    }

    public boolean update(Catheter catheter){
        return catheterDao.update(catheter);
    }

    public Catheter queryData(String id){
        return catheterDao.queryData(id);
    }

    public List<Catheter> queryList(Map<String,Object> map){
        return catheterDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return catheterDao.queryNum(map);}
}
