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

    /**
     * 统计分析--出入科--列表展示统计（按天）
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByDay(Map<String,Object> map){
        return inOutIcuStatisticsDao.getListByDay(map);
    }

    /**
     * 统计分析--出入科--列表展示统计总量（按天）
     * @param map
     * @return
     */
    public List<Map<String,Object>> getTotalByDay(Map<String,Object> map){
        return inOutIcuStatisticsDao.getTotalByDay(map);
    }

    /**
     * 统计分析--出入科--列表展示统计（按月）
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByMonth(Map<String,Object> map){
        return inOutIcuStatisticsDao.getListByMonth(map);
    }

    /**
     * 统计分析--出入科--列表展示统计总量（按月）
     * @param map
     * @return
     */
    public List<Map<String,Object>> getTotalByMonth(Map<String,Object> map){
        return inOutIcuStatisticsDao.getTotalByMonth(map);
    }

    /**
     * 根据条件查询出入科对象
     * @param map
     * @return
     */
    public List<InOutIcuStatistics> queryDataByDate(Map<String,Object> map){
        return inOutIcuStatisticsDao.queryDataByDate(map);
    }
}
