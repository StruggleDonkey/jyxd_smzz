package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BedArrange;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BedArrangeDao {

    /**
     * 新增一条床位安排表记录
     *
     * @param bedArrange
     * @return boolean
     */
    boolean insert(BedArrange bedArrange);

    /**
     * 根据主键id查询一条床位安排表记录
     *
     * @param id
     * @return BedArrange
     */
    BedArrange queryData(String id);

    /**
     * 更新一条床位安排表记录
     *
     * @param bedArrange
     * @return boolean
     */
    boolean update(BedArrange bedArrange);

    /**
     * 根据条件分页查询床位安排表记录列表
     *
     * @param map
     * @return list
     */
    List<BedArrange> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     *
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 查询床位安排列表信息
     *
     * @return
     */
    List<Map<String, Object>> getBedArrangeList(Map<String, Object> map);

    /**
     * 根据条件查询床位安排实体对象
     *
     * @param map
     * @return
     */
    BedArrange queryDataByCode(Map<String, Object> map);

    /**
     * 根据床位号查询床位安排
     *
     * @param bedCode
     * @return
     */
    BedArrange queryDataByBedCode(String bedCode);

    /**
     * 根据病人号查询床位安排
     *
     * @param patientId
     * @return
     */
    BedArrange queryDataByPatientId(String patientId);
}
