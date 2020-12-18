package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BasicNursingDao;
import com.jyxd.web.data.basic.BasicNursing;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BasicNursingService {

    @Autowired
    private BasicNursingDao basicNursingDao;

    public boolean insert(BasicNursing basicNursing){
        return basicNursingDao.insert(basicNursing);
    }

    public boolean update(BasicNursing basicNursing){
        return basicNursingDao.update(basicNursing);
    }

    public BasicNursing queryData(String id){
        return basicNursingDao.queryData(id);
    }

    public List<BasicNursing> queryList(Map<String,Object> map){
        return basicNursingDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return basicNursingDao.queryNum(map);}

    /**
     * 快捷录入--护理单--查询所有在科病人护理信息
     * @param map
     * @return
     */
    public List<Map<String,Object>> getNursingList(Map<String,Object> map){
        return basicNursingDao.getNursingList(map);
    }

    /**
     * 根据病人id 时间 code  查询基础护理对象
     * @param map
     * @return
     */
    public BasicNursing getDataByPatientIdAndCodeAndTime(Map<String,Object> map){
        return basicNursingDao.getDataByPatientIdAndCodeAndTime(map);
    }

    /**
     * 根据病人id 时间  查询基础护理对象列表
     * @param map
     * @return
     */
    public List<BasicNursing> queryListByPatientIdAndTime(Map<String,Object> map){
        return basicNursingDao.queryListByPatientIdAndTime(map);
    }

    /**
     * 护理文书--护理单--基础护理--根据病人id查询基础护理列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getListByPatientId(Map<String,Object> map){
        return basicNursingDao.getListByPatientId(map);
    }

    /**
     * 根据病人id查询某个时间段内静脉置管信息
     * @param map
     * @return
     */
    public JSONArray getVeinDrainage(Map<String,Object> map){
        JSONArray array=new JSONArray();
        List<Map<String,Object>> list=basicNursingDao.getVeinDrainage(map);
        if(list!=null && list.size()>0){
            array=JSONArray.fromObject(list);
        }
        return array;
    }

    /**
     * 根据病人id查询某个时间段内动脉置管信息
     * @param map
     * @return
     */
    public JSONArray getArtery(Map<String,Object> map){
        JSONArray array=new JSONArray();
        List<Map<String,Object>> list=basicNursingDao.getArtery(map);
        if(list!=null && list.size()>0){
            array=JSONArray.fromObject(list);
        }
        return array;
    }

    /**
     * 根据病人id查询某个时间段内引流管信息
     * @param map
     * @return
     */
    public JSONArray getDrainage(Map<String,Object> map){
        JSONArray array=new JSONArray();
        List<Map<String,Object>> list=basicNursingDao.getDrainage(map);
        if(list!=null && list.size()>0){
            array=JSONArray.fromObject(list);
        }
        return array;
    }


}
