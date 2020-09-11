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
}
