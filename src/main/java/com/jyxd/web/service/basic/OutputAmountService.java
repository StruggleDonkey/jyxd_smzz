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

    /**
     * 快捷录入--体温单--体温表单--根据时间查询在科病人体温表单数据
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByTime(Map<String,Object> map){
        return outputAmountDao.getListByTime(map);
    }

    /**
     * 根据时间查询出量对象
     * @param map
     * @return
     */
    public OutputAmount queryDataByTime(Map<String,Object> map){
        return outputAmountDao.queryDataByTime(map);
    }

    /**
     * 护理文书--护理单--出量--查询病人出量列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getPatientOutputList(Map<String,Object> map){
        return outputAmountDao.getPatientOutputList(map);
    }
}
