package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.HandoverDao;
import com.jyxd.web.data.basic.Handover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HandoverService {

    @Autowired
    private HandoverDao handoverDao;

    public boolean insert(Handover handover){
        return handoverDao.insert(handover);
    }

    public boolean update(Handover handover){
        return handoverDao.update(handover);
    }

    public Handover queryData(String id){
        return handoverDao.queryData(id);
    }

    public List<Handover> queryList(Map<String,Object> map){
        return handoverDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return handoverDao.queryNum(map);}

    /**
     * 根据主键id查询交接班详情
     * @param map
     * @return
     */
    public Map<String,Object> printHandover(Map<String,Object> map){
        return handoverDao.printHandover(map);
    }

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return handoverDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return handoverDao.getNum(map);}

}
