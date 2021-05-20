package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.InOutAmount;
import com.jyxd.web.data.basic.PrimaryCare;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PrimaryCareDao {

    /**
     * 新增一条基础护理表(新版-吕梁医院使用)记录
     * @param primaryCare
     * @return boolean
     */
    boolean insert(PrimaryCare primaryCare);

    /**
     * 根据主键id查询一条基础护理表(新版-吕梁医院使用)记录
     * @param id
     * @return PrimaryCare
     */
    PrimaryCare queryData(String id);

    /**
     * 更新一条基础护理表(新版-吕梁医院使用)记录
     * @param primaryCare
     * @return boolean
     */
    boolean update(PrimaryCare primaryCare);

    /**
     * 根据条件分页基础护理表(新版-吕梁医院使用)记录列表
     * @param map
     * @return list
     */
    List<PrimaryCare> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 病人管理-护理文书-护理单文书-基础护理-根据条件查询基础护理表(新版-吕梁医院使用)列表
     * @param map
     * @return list
     */
    List<Map<String, Object>>  getPrimaryCareList(Map<String, Object> map);

    /**
     * 病人管理-护理文书-护理单文书-基础护理-根据条件查询出入量列表数量
     * @param map
     * @return
     */
    int getPrimaryCareNum(Map<String, Object> map);

}
