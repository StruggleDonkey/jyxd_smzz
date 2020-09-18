package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.SchedualDao;
import com.jyxd.web.data.basic.Schedual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SchedualService {

    @Autowired
    private SchedualDao schedualDao;

    public boolean insert(Schedual schedual){
        return schedualDao.insert(schedual);
    }

    public boolean update(Schedual schedual){
        return schedualDao.update(schedual);
    }

    public Schedual queryData(String id){
        return schedualDao.queryData(id);
    }

    public List<Schedual> queryList(Map<String,Object> map){
        return schedualDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return schedualDao.queryNum(map);}
}
