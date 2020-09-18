package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.OutputAmountDao;
import com.jyxd.web.data.basic.OutputAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OutputAmountService {

    @Autowired
    private OutputAmountDao outputAmountDao;

    public boolean insert(OutputAmount outputAmount){
        return outputAmountDao.insert(outputAmount);
    }

    public boolean update(OutputAmount outputAmount){
        return outputAmountDao.update(outputAmount);
    }

    public OutputAmount queryData(String id){
        return outputAmountDao.queryData(id);
    }

    public List<OutputAmount> queryList(Map<String,Object> map){
        return outputAmountDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return outputAmountDao.queryNum(map);}
}
