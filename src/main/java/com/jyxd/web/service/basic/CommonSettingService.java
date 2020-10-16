package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.CommonSettingDao;
import com.jyxd.web.data.basic.CommonSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommonSettingService {

    @Autowired
    private CommonSettingDao commonSettingDao;

    public boolean insert(CommonSetting commonSetting){
        return commonSettingDao.insert(commonSetting);
    }

    public boolean update(CommonSetting commonSetting){
        return commonSettingDao.update(commonSetting);
    }

    public CommonSetting queryData(String id){
        return commonSettingDao.queryData(id);
    }

    public List<CommonSetting> queryList(Map<String,Object> map){
        return commonSettingDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return commonSettingDao.queryNum(map);}

    /**
     * 查询排班时间的通用设置列表
     * @param map
     * @return
     */
    public List<CommonSetting> getSchedualTimeList(Map<String,Object> map){
        return commonSettingDao.getSchedualTimeList(map);
    }

    /**
     * 系统设置--通用设置--监护仪采集频率/默认首页--查询监护仪频率或默认首页
     * @param map
     * @return
     */
    public CommonSetting getCommonSettingByType(Map<String,Object> map){
        return commonSettingDao.getCommonSettingByType(map);
    }
}
