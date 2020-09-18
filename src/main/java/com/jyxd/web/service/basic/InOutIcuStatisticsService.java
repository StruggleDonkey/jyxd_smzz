package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.InOutIcuStatisticsDao;
import com.jyxd.web.data.basic.InOutIcuStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InOutIcuStatisticsService {

    @Autowired
    private InOutIcuStatisticsDao inOutIcuStatisticsDao;

    public boolean insert(InOutIcuStatistics inOutIcuStatistics){
        return inOutIcuStatisticsDao.insert(inOutIcuStatistics);
    }

    public boolean update(InOutIcuStatistics inOutIcuStatistics){
        return inOutIcuStatisticsDao.update(inOutIcuStatistics);
    }

    public InOutIcuStatistics queryData(String id){
        return inOutIcuStatisticsDao.queryData(id);
    }

    public List<InOutIcuStatistics> queryList(Map<String,Object> map){
        return inOutIcuStatisticsDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return inOutIcuStatisticsDao.queryNum(map);}
}
