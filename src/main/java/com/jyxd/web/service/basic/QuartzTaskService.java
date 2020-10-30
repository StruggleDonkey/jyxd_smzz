package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.QuartzTaskDao;
import com.jyxd.web.data.basic.QuartzTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("quartzTaskService")
@Transactional
public class QuartzTaskService {

    @Autowired
    private QuartzTaskDao quartzTaskDao;

    public boolean insert(QuartzTask quartzTask){
        return quartzTaskDao.insert(quartzTask);
    }

    public boolean update(QuartzTask quartzTask){
        return quartzTaskDao.update(quartzTask);
    }

    public QuartzTask queryData(String id){
        return quartzTaskDao.queryData(id);
    }

    public List<QuartzTask> queryList(Map<String,Object> map){
        return quartzTaskDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return quartzTaskDao.queryNum(map);}

    /**
     * 根据jobName 和 jobGroup 查询是否有任务
     * @param map
     * @return
     */
    public QuartzTask queryDataByNameAndGroup(Map<String,Object> map){
        return quartzTaskDao.queryDataByNameAndGroup(map);
    }

    /**
     * 系统设置--任务管理--查询任务列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getList(Map<String,Object> map){
        return quartzTaskDao.getList(map);
    }
}
