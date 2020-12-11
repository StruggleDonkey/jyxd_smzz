package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.VitalSignDao;
import com.jyxd.web.data.basic.VitalSign;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VitalSignService {

    @Autowired
    private VitalSignDao vitalSignDao;

    public boolean insert(VitalSign vitalSign){
        return vitalSignDao.insert(vitalSign);
    }

    public boolean update(VitalSign vitalSign){
        return vitalSignDao.update(vitalSign);
    }

    public VitalSign queryData(String id){
        return vitalSignDao.queryData(id);
    }

    public List<VitalSign> queryList(Map<String,Object> map){
        return vitalSignDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return vitalSignDao.queryNum(map);}

    /**
     * 根据时间和code查询对象
     * @param map
     * @return
     */
    public VitalSign queryDataByTimeAndCode(Map<String,Object> map){
        return vitalSignDao.queryDataByTimeAndCode(map);
    }

    /**
     * 根据时间和病人主键id查询对象列表
     * @param map
     * @return
     */
    public List<VitalSign> queryListByTime(Map<String,Object> map){
        return vitalSignDao.queryListByTime(map);
    }

    /**
     * 护理文书--护理单--生命体征--根据病人主键id 查询生命体征列表
     * @return
     */
    public List<Map<String,Object>> getList(Map<String,Object> map){
        return vitalSignDao.getList(map);
    }

    /**
     * 根据病人id获取某个时间段生命体征最大值
     * @param map
     * @return
     */
    public JSONObject getMaxVitalSign(Map<String,Object> map){
        JSONObject jsonObject=new JSONObject();
        Map<String,Object> maxMap=vitalSignDao.getMaxVitalSign(map);
        if(maxMap!=null){
            jsonObject=JSONObject.fromObject(maxMap);
        }
        return jsonObject;
    }

    /**
     * 根据病人id获取某个时间段生命体征最小值
     * @param map
     * @return
     */
    public JSONObject getMinVitalSign(Map<String,Object> map){
        JSONObject jsonObject=new JSONObject();
        Map<String,Object> maxMap=vitalSignDao.getMinVitalSign(map);
        if(maxMap!=null){
            jsonObject=JSONObject.fromObject(maxMap);
        }
        return jsonObject;
    }

    /**
     * 根据病人id获取某个时间生命体征值
     * @param map
     * @return
     */
    public JSONObject getNowVitalSign(Map<String,Object> map){
        JSONObject jsonObject=new JSONObject();
        Map<String,Object> maxMap=vitalSignDao.getNowVitalSign(map);
        if(maxMap!=null){
            jsonObject=JSONObject.fromObject(maxMap);
        }
        return jsonObject;
    }

}
