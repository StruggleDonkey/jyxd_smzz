package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.InputAmountDao;
import com.jyxd.web.data.basic.InputAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InputAmountService {

    @Autowired
    private InputAmountDao inputAmountDao;

    public boolean insert(InputAmount inputAmount){
        return inputAmountDao.insert(inputAmount);
    }

    public boolean update(InputAmount inputAmount){
        return inputAmountDao.update(inputAmount);
    }

    public InputAmount queryData(String id){
        return inputAmountDao.queryData(id);
    }

    public List<InputAmount> queryList(Map<String,Object> map){
        return inputAmountDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return inputAmountDao.queryNum(map);}
}
