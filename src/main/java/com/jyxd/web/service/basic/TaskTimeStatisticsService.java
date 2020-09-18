package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.TaskTimeStatisticsDao;
import com.jyxd.web.data.basic.TaskTimeStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TaskTimeStatisticsService {

    @Autowired
    private TaskTimeStatisticsDao taskTimeStatisticsDao;

    public boolean insert(TaskTimeStatistics taskTimeStatistics){
        return taskTimeStatisticsDao.insert(taskTimeStatistics);
    }

    public boolean update(TaskTimeStatistics taskTimeStatistics){
        return taskTimeStatisticsDao.update(taskTimeStatistics);
    }

    public TaskTimeStatistics queryData(String id){
        return taskTimeStatisticsDao.queryData(id);
    }

    public List<TaskTimeStatistics> queryList(Map<String,Object> map){
        return taskTimeStatisticsDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return taskTimeStatisticsDao.queryNum(map);}
}
