package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.DiagnoseCoefficientDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DiagnoseCoefficientDictionaryDao {

    /**
     * 新增一条ICU主要疾病诊断类型表记录
     * @param diagnoseCoefficientDictionary
     * @return boolean
     */
    boolean insert(DiagnoseCoefficientDictionary diagnoseCoefficientDictionary);

    /**
     * 根据主键id查询一条ICU主要疾病诊断类型表记录
     * @param id
     * @return DiagnoseCoefficientDictionary
     */
    DiagnoseCoefficientDictionary queryData(String id);

    /**
     * 更新一条ICU主要疾病诊断类型表记录
     * @param diagnoseCoefficientDictionary
     * @return boolean
     */
    boolean update(DiagnoseCoefficientDictionary diagnoseCoefficientDictionary);

    /**
     * 根据条件分页查询ICU主要疾病诊断类型表记录列表
     * @param map
     * @return list
     */
    List<DiagnoseCoefficientDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件查询对象
     * @param map
     * @return
     */
    DiagnoseCoefficientDictionary queryDataByName(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
