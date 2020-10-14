package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.DepartmentDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DepartmentDictionaryDao {

    /**
     * 新增一条科室字典表记录
     * @param departmentDictionary
     * @return boolean
     */
    boolean insert(DepartmentDictionary departmentDictionary);

    /**
     * 根据主键id查询一条科室字典表记录
     * @param id
     * @return DepartmentDictionary
     */
    DepartmentDictionary queryData(String id);

    /**
     * 更新一条科室字典表记录
     * @param departmentDictionary
     * @return boolean
     */
    boolean update(DepartmentDictionary departmentDictionary);

    /**
     * 根据条件分页查询科室字典列表
     * @param map
     * @return list
     */
    List<DepartmentDictionary> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 查询科室字典bean类列表
     * @param map
     * @return list
     */
    List<DepartmentDictionary> queryDataList(Map<String, Object> map);

    /**
     * 根据code查询科室字典对象
     * @param map
     * @return
     */
    DepartmentDictionary queryDataByCode(Map<String, Object> map);
}
