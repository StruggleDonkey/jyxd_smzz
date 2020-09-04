package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.AbnormalVitalSignsDao;
import com.jyxd.web.data.basic.AbnormalVitalSigns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AbnormalVitalSignsService {

    @Autowired
    private AbnormalVitalSignsDao abnormalVitalSignsDao;

    public boolean insert(AbnormalVitalSigns abnormalVitalSigns){
        return abnormalVitalSignsDao.insert(abnormalVitalSigns);
    }

    public boolean update(AbnormalVitalSigns abnormalVitalSigns){
        return abnormalVitalSignsDao.update(abnormalVitalSigns);
    }

    public AbnormalVitalSigns queryData(String id){
        return abnormalVitalSignsDao.queryData(id);
    }

    public List<AbnormalVitalSigns> queryList(Map<String,Object> map){
        return abnormalVitalSignsDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return abnormalVitalSignsDao.queryNum(map);}
}
