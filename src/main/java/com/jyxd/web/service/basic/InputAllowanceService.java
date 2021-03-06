package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.InputAllowanceDao;
import com.jyxd.web.data.basic.InputAllowance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InputAllowanceService {

    @Autowired
    private InputAllowanceDao inputAllowanceDao;

    public boolean insert(InputAllowance inputAllowance){
        return inputAllowanceDao.insert(inputAllowance);
    }

    public boolean update(InputAllowance inputAllowance){
        return inputAllowanceDao.update(inputAllowance);
    }

    public InputAllowance queryData(String id){
        return inputAllowanceDao.queryData(id);
    }

    public List<InputAllowance> queryList(Map<String,Object> map){
        return inputAllowanceDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return inputAllowanceDao.queryNum(map);}

    /**
     * 根据医嘱主键查询入量余量列表
     * @param map
     * @return
             */
    public List<InputAllowance> queryListByOrderNo(Map<String,Object> map){
        return inputAllowanceDao.queryListByOrderNo(map);
    }

    /**
     * 根据医嘱主键查询余量 从大到小排序
     * @param map
     * @return
     */
    public List<String> getAllowanceDosageByOrderNo(Map<String,Object> map){
        return inputAllowanceDao.getAllowanceDosageByOrderNo(map);
    }
}
