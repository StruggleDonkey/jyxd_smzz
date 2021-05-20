package com.jyxd.web.service.basic;


import com.jyxd.web.dao.basic.InOutAmountDao;
import com.jyxd.web.dao.basic.PrimaryCareDao;
import com.jyxd.web.data.basic.InOutAmount;
import com.jyxd.web.data.basic.PrimaryCare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PrimaryCareService {

    @Autowired
    private PrimaryCareDao primaryCareDao;

    public boolean insert(PrimaryCare primaryCare){
        return primaryCareDao.insert(primaryCare);
    }

    public boolean update(PrimaryCare primaryCare){
        return primaryCareDao.update(primaryCare);
    }

    public PrimaryCare queryData(String id){
        return primaryCareDao.queryData(id);
    }

    public List<PrimaryCare> queryList(Map<String,Object> map){
        return primaryCareDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return primaryCareDao.queryNum(map);}

    /**
     * 病人管理-护理文书-护理单文书-基础护理-根据条件查询基础护理表(新版-吕梁医院使用)列表
     * @param map
     * @return list
     */
    public List<Map<String,Object>> getPrimaryCareList(Map<String, Object> map){
        return primaryCareDao.getPrimaryCareList(map);
    }

    /**
     * 病人管理-护理文书-护理单文书-基础护理-根据条件查询基础护理表(新版-吕梁医院使用)列表数量
     * @param map
     * @return
     */
    public int getPrimaryCareNum(Map<String, Object> map){
        return primaryCareDao.getPrimaryCareNum(map);
    }

}
