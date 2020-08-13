package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.DiagnoseCoefficinetItemDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DiagnoseCoefficinetItemDictionaryDao {

    /**
     * 新增一条ICU主要疾病诊断分类系数表录
     * @param diagnoseCoefficinetItemDictionary
     * @return boolean
     */
    boolean insert(DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary);

    /**
     * 根据主键id查询一条ICU主要疾病诊断分类系数表记录
     * @param id
     * @return DiagnoseCoefficinetItemDictionary
     */
    DiagnoseCoefficinetItemDictionary queryData(String id);

    /**
     * 更新一条床ICU主要疾病诊断分类系数表记录
     * @param diagnoseCoefficinetItemDictionary
     * @return boolean
     */
    boolean update(DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary);

    /**
     * 根据条件分页查询ICU主要疾病诊断分类系数表列表
     * @param map
     * @return list
     */
    List<DiagnoseCoefficinetItemDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件查询对象
     * @param map
     * @return
     */
    DiagnoseCoefficinetItemDictionary queryDataByName(Map<String, Object> map);

    /**
     * 根据条件分页查询ICU主要疾病诊断分类系数表列表--多表查询
     * @param map
     * @return list
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
