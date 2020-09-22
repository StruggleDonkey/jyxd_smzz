package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.SurgeryNursingMaintain;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SurgeryNursingMaintainDao {

    /**
     * 新增一条围手术期护理记录表记录
     * @param surgeryNursingMaintain
     * @return boolean
     */
    boolean insert(SurgeryNursingMaintain surgeryNursingMaintain);

    /**
     * 根据主键id查询一条围手术期护理记录表记录
     * @param id
     * @return SurgeryNursingMaintain
     */
    SurgeryNursingMaintain queryData(String id);

    /**
     * 更新一条围手术期护理记录表记录
     * @param surgeryNursingMaintain
     * @return boolean
     */
    boolean update(SurgeryNursingMaintain surgeryNursingMaintain);

    /**
     * 根据条件分页查询围手术期护理记录表记录列表
     * @param map
     * @return list
     */
    List<SurgeryNursingMaintain> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
