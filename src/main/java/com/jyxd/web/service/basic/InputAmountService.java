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

    public int getNumByPatientId(Map<String,Object> map){return inputAmountDao.getNumByPatientId(map);}

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

    /**
     * 查询病人某段时间内的入量总数
     * @param map
     * @return
     */
    public String getInTotal(Map<String,Object> map){
        return inputAmountDao.getInTotal(map);
    }

    /**
     * 查询病人某段时间内各个入量类型的入量总数
     * @param map
     * @return
     */
    public List<Map<String,Object>> getInAnalyze(Map<String,Object> map){
        return inputAmountDao.getInAnalyze(map);
    }

    /**
     * 按天查询某段时间内病人出入量列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getInAndOutAnalyzeByDay(Map<String,Object> map){
        return inputAmountDao.getInAndOutAnalyzeByDay(map);
    }

    /**
     * 按小时查询某段时间内病人出入量列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getInAndOutAnalyzeByTime(Map<String,Object> map){
        return inputAmountDao.getInAndOutAnalyzeByTime(map);
    }

    /**
     * 护理文书-护理单文书-入量-核对签名-根据入量主键id查询出入量详情
     * @param map
     * @return
     */
    public Map<String,Object> getDataDetailsById(Map<String,Object> map){
        return inputAmountDao.getDataDetailsById(map);
    }

}
