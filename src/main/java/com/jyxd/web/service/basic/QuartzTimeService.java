package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.QuartzTimeDao;
import com.jyxd.web.data.basic.QuartzTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QuartzTimeService {

    @Autowired
    private QuartzTimeDao quartzTimeDao;

    public boolean insert(QuartzTime quartzTime){
        return quartzTimeDao.insert(quartzTime);
    }

    public boolean update(QuartzTime quartzTime){
        return quartzTimeDao.update(quartzTime);
    }

    public QuartzTime queryData(String id){
        return quartzTimeDao.queryData(id);
    }

    public List<QuartzTime> queryList(Map<String,Object> map){
        return quartzTimeDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return quartzTimeDao.queryNum(map);}
}
