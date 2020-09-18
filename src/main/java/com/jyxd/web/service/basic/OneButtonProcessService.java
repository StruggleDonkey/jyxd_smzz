package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.OneButtonProcessDao;
import com.jyxd.web.data.basic.OneButtonProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OneButtonProcessService {

    @Autowired
    private OneButtonProcessDao oneButtonProcessDao;

    public boolean insert(OneButtonProcess oneButtonProcess){
        return oneButtonProcessDao.insert(oneButtonProcess);
    }

    public boolean update(OneButtonProcess oneButtonProcess){
        return oneButtonProcessDao.update(oneButtonProcess);
    }

    public OneButtonProcess queryData(String id){
        return oneButtonProcessDao.queryData(id);
    }

    public List<OneButtonProcess> queryList(Map<String,Object> map){
        return oneButtonProcessDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return oneButtonProcessDao.queryNum(map);}
}
