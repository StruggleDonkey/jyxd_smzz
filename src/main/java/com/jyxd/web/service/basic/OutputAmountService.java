package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.OutputAmountDao;
import com.jyxd.web.data.basic.OutputAmount;
import net.sf.json.JSONObject;
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

    /**
     * 根据病人id查询病人在某个时间段的出量汇总信息
     * @param map
     * @return
     */
    public JSONObject getOutAmount(Map<String,Object> map){
        JSONObject jsonObject=new JSONObject();
        Map<String,Object> outMap=outputAmountDao.getOutAmount(map);
        if(outMap!=null){
            jsonObject=JSONObject.fromObject(outMap);
        }
        return jsonObject;
    }

    /**
     * 查询病人某段时间内的出量总数
     * @param map
     * @return
     */
    public String getOutTotal(Map<String,Object> map){
        return outputAmountDao.getOutTotal(map);
    }

    /**
     * 查询病人某段时间内各个出量类型的入量总数
     * @param map
     * @return
     */
    public List<Map<String,Object>> getOutAnalyze(Map<String,Object> map){
        return outputAmountDao.getOutAnalyze(map);
    }

    /**
     * 护理文书--护理单--出量--查询病人出量列表(新版 为了打印可以和出入量一起打印)
     * @param map
     * @return
     */
    public List<Map<String,Object>> getNewPatientOutputList(Map<String,Object> map){
        return outputAmountDao.getNewPatientOutputList(map);
    }

    /**
     * 测试
     * @param map
     * @return
     */
    public List<Map<String,Object>> test(Map<String,Object> map){
        return outputAmountDao.test(map);
    }
}
