package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BedUseStatisticsDao;
import com.jyxd.web.data.basic.BedUseStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BedUseStatisticsService {

    @Autowired
    private BedUseStatisticsDao bedUseStatisticsDao;

    public boolean insert(BedUseStatistics bedUseStatistics){
        return bedUseStatisticsDao.insert(bedUseStatistics);
    }

    public boolean update(BedUseStatistics bedUseStatistics){
        return bedUseStatisticsDao.update(bedUseStatistics);
    }

    public BedUseStatistics queryData(String id){
        return bedUseStatisticsDao.queryData(id);
    }

    public List<BedUseStatistics> queryList(Map<String,Object> map){
        return bedUseStatisticsDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return bedUseStatisticsDao.queryNum(map);}

    /**
     * 统计分析--床位使用--日期分析（按天）
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByTimeDay(Map<String,Object> map){
        return bedUseStatisticsDao.getListByTimeDay(map);
    }

    /**
     * 统计分析--床位使用--日期分析（按月）
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByTimeMonth(Map<String,Object> map){
        return bedUseStatisticsDao.getListByTimeMonth(map);
    }
}
