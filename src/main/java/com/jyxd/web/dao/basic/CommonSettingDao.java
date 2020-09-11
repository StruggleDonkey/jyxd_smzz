package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.CommonSetting;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommonSettingDao {

    /**
     * 新增一条通用设置表记录
     * @param commonSetting
     * @return boolean
     */
    boolean insert(CommonSetting commonSetting);

    /**
     * 根据主键id查询一条通用设置表记录
     * @param id
     * @return CommonSetting
     */
    CommonSetting queryData(String id);

    /**
     * 更新一条通用设置表记录
     * @param commonSetting
     * @return boolean
     */
    boolean update(CommonSetting commonSetting);

    /**
     * 根据条件分页查询通用设置表记录列表
     * @param map
     * @return list
     */
    List<CommonSetting> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
