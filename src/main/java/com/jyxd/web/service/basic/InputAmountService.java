package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.InputAmountDao;
import com.jyxd.web.data.basic.InputAmount;
import net.sf.json.JSONObject;
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

    /**
     * 根据时间查询入量对象
     * @param map
     * @return
     */
    public InputAmount queryDataByTime(Map<String,Object> map){
        return inputAmountDao.queryDataByTime(map);
    }

    /**
     * 护理文书--护理单--入量--根据病人id查询入量列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByPatientId(Map<String,Object> map){
        return inputAmountDao.getListByPatientId(map);
    }

    /**
     * 根据病人id查询病人在某个时间段的入量汇总信息
     * @param map
     * @return
     */
    public JSONObject getInAmount(Map<String,Object> map){
        JSONObject jsonObject=new JSONObject();
        Map<String,Object> outMap=inputAmountDao.getInAmount(map);
        if(outMap!=null){
            jsonObject=JSONObject.fromObject(outMap);
        }
        return jsonObject;
    }
}
