package com.jyxd.web.service.basic;


import com.jyxd.web.dao.basic.InOutAmountDao;
import com.jyxd.web.data.basic.InOutAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PrimaryCareService {

    @Autowired
    private InOutAmountDao inOutAmountDao;

    public boolean insert(InOutAmount inOutAmount){
        return inOutAmountDao.insert(inOutAmount);
    }

    public boolean update(InOutAmount inOutAmount){
        return inOutAmountDao.update(inOutAmount);
    }

    public InOutAmount queryData(String id){
        return inOutAmountDao.queryData(id);
    }

    public List<InOutAmount> queryList(Map<String,Object> map){
        return inOutAmountDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return inOutAmountDao.queryNum(map);}

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表
     * @param map
     * @return list
     */
    public List<Map<String,Object>> getInOutAmountList(Map<String, Object> map){
        return inOutAmountDao.getInOutAmountList(map);
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表数量
     * @param map
     * @return
     */
    public int getInOutAmountNum(Map<String, Object> map){
        return inOutAmountDao.getInOutAmountNum(map);
    }

    /**
     * 病人管理-护理文书-护理单文书-出入量-根据条件查询出入量列表(新的直接查10个字段)
     * @param map
     * @return list
     */
    public List<Map<String,Object>> queryInOutAmountList(Map<String, Object> map){
        return inOutAmountDao.queryInOutAmountList(map);
    }
}
