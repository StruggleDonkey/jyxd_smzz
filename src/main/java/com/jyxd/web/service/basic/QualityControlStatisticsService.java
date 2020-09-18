package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.QualityControlStatisticsDao;
import com.jyxd.web.data.basic.QualityControlStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class QualityControlStatisticsService {

    @Autowired
    private QualityControlStatisticsDao qualityControlStatisticsDao;

    public boolean insert(QualityControlStatistics qualityControlStatistics){
        return qualityControlStatisticsDao.insert(qualityControlStatistics);
    }

    public boolean update(QualityControlStatistics qualityControlStatistics){
        return qualityControlStatisticsDao.update(qualityControlStatistics);
    }

    public QualityControlStatistics queryData(String id){
        return qualityControlStatisticsDao.queryData(id);
    }

    public List<QualityControlStatistics> queryList(Map<String,Object> map){
        return qualityControlStatisticsDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return qualityControlStatisticsDao.queryNum(map);}
}
