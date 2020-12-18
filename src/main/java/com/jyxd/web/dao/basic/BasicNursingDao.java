package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BasicNursing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BasicNursingDao {

    /**
     * 新增一条基础护理表记录
     * @param basicNursing
     * @return boolean
     */
    boolean insert(BasicNursing basicNursing);

    /**
     * 根据主键id查询一条基础护理表记录
     * @param id
     * @return BasicNursing
     */
    BasicNursing queryData(String id);

    /**
     * 更新一条基础护理表记录
     * @param basicNursing
     * @return boolean
     */
    boolean update(BasicNursing basicNursing);

    /**
     * 根据条件分页查询基础护理表记录列表
     * @param map
     * @return list
     */
    List<BasicNursing> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 快捷录入--护理单--查询所有在科病人护理信息
     * @param map
     * @return
     */
    List<Map<String, Object>> getNursingList(Map<String, Object> map);

    /**
     * 根据病人id 时间 code  查询基础护理对象
     * @param map
     * @return
     */
     BasicNursing getDataByPatientIdAndCodeAndTime(Map<String,Object> map);

    /**
     * 根据病人id 时间  查询基础护理对象列表
     * @param map
     * @return
     */
     List<BasicNursing> queryListByPatientIdAndTime(Map<String,Object> map);

    /**
     * 护理文书--护理单--基础护理--根据病人id查询基础护理列表
     * @param map
     * @return
     */
     List<Map<String,Object>> getListByPatientId(Map<String,Object> map);

    /**
     * 根据病人id查询某个时间段内静脉置管信息
     * @param map
     * @return
     */
     List<Map<String,Object>> getVeinDrainage(Map<String,Object> map);

    /**
     * 根据病人id查询某个时间段内动脉置管信息
     * @param map
     * @return
     */
    List<Map<String,Object>> getArtery(Map<String,Object> map);

    /**
     * 根据病人id查询某个时间段内引流管信息
     * @param map
     * @return
     */
    List<Map<String,Object>> getDrainage(Map<String,Object> map);
}
