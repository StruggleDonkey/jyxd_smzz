package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BloodSugarDao;
import com.jyxd.web.data.basic.BloodSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BloodSugarService {

    @Autowired
    private BloodSugarDao bloodSugarDao;

    public boolean insert(BloodSugar bloodSugar){
        return bloodSugarDao.insert(bloodSugar);
    }

    public boolean update(BloodSugar bloodSugar){
        return bloodSugarDao.update(bloodSugar);
    }

    public BloodSugar queryData(String id){
        return bloodSugarDao.queryData(id);
    }

    public List<BloodSugar> queryList(Map<String,Object> map){
        return bloodSugarDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return bloodSugarDao.queryNum(map);}

    /**
     * 根据时间查询在科病人的血糖信息
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByTime(Map<String,Object> map){
        return bloodSugarDao.getListByTime(map);
    }

    /**
     * 根据病人id code 时间 查询血糖对象
     * @param map
     * @return
     */
    public BloodSugar queryDataByCodeAndPatientId(Map<String,Object> map){
        return bloodSugarDao.queryDataByCodeAndPatientId(map);
    }

    /**
     * 根据时间和病人id查询血糖列表
     * @param map
     * @return
     */
    public List<BloodSugar> queryListByTimeAndPatientId(Map<String,Object> map){
        return bloodSugarDao.queryListByTimeAndPatientId(map);
    }

    /**
     * 护理文书--血糖监测--根据病人id 和 时间查询血糖列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getList(Map<String,Object> map){
        return bloodSugarDao.getList(map);
    }
}
