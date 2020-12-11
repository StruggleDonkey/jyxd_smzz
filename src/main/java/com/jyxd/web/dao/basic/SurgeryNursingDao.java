package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.SurgeryNursing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SurgeryNursingDao {

    /**
     * 新增一条围手术期护理基本信息表记录
     * @param surgeryNursing
     * @return boolean
     */
    boolean insert(SurgeryNursing surgeryNursing);

    /**
     * 根据主键id查询一条围手术期护理基本信息表记录
     * @param id
     * @return SurgeryNursing
     */
    SurgeryNursing queryData(String id);

    /**
     * 更新一条围手术期护理基本信息表记录
     * @param surgeryNursing
     * @return boolean
     */
    boolean update(SurgeryNursing surgeryNursing);

    /**
     * 根据条件分页查询围手术期护理基本信息表记录列表
     * @param map
     * @return list
     */
    List<SurgeryNursing> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询围手术期护理基本信息表记录列表
     * @param map
     * @return list
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int getNum(Map<String, Object> map);

    /**
     * 护理文书--围手术期护理单--首页---历史记录--选择
     * @param map
     * @return list
     */
    List<Map<String, Object>> queryDataByMaintenanceId(Map<String, Object> map);

    /**
     * 根据 maintenanceId 查询围手术期护理基本信息表记录列表
     * @param maintenanceId
     * @return list
     */
    List<SurgeryNursing> queryListByMaintenanceId(String maintenanceId);
}
