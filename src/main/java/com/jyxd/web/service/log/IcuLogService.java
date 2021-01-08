package com.jyxd.web.service.log;

import com.jyxd.web.dao.log.IcuLogDao;
import com.jyxd.web.data.log.IcuLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IcuLogService {

    @Autowired
    private IcuLogDao icuLogDao;

    public boolean insert(IcuLog icuLog){
        return icuLogDao.insert(icuLog);
    }

    public boolean update(IcuLog icuLog){
        return icuLogDao.update(icuLog);
    }

    public IcuLog queryData(String id){
        return icuLogDao.queryData(id);
    }

    public List<IcuLog> queryList(Map<String,Object> map){
        return icuLogDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return icuLogDao.queryNum(map);}

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return icuLogDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return icuLogDao.getNum(map);}

    /**
     * 病人管理-患者日志-日志列表-编辑回显-根据主键id查询对象详情
     * @param map
     * @return
     */
    public Map<String,Object> getEditData(Map<String,Object> map){
        return icuLogDao.getEditData(map);
    }

}
