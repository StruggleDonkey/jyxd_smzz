package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BasicNursingDao;
import com.jyxd.web.data.basic.BasicNursing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BasicNursingService {

    @Autowired
    private BasicNursingDao basicNursingDao;

    public boolean insert(BasicNursing basicNursing){
        return basicNursingDao.insert(basicNursing);
    }

    public boolean update(BasicNursing basicNursing){
        return basicNursingDao.update(basicNursing);
    }

    public BasicNursing queryData(String id){
        return basicNursingDao.queryData(id);
    }

    public List<BasicNursing> queryList(Map<String,Object> map){
        return basicNursingDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return basicNursingDao.queryNum(map);}

    /**
     * 快捷录入--护理单--查询所有在科病人护理信息
     * @param map
     * @return
     */
    public List<Map<String,Object>> getNursingList(Map<String,Object> map){
        return basicNursingDao.getNursingList(map);
    }
}