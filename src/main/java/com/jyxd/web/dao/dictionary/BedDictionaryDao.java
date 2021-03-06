package com.jyxd.web.dao.dictionary;

import com.jyxd.web.data.dictionary.BedDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BedDictionaryDao {

    /**
     * 新增一条床位字典表记录
     * @param bedDictionary
     * @return boolean
     */
    boolean insert(BedDictionary bedDictionary);

    /**
     * 根据主键id查询一条床位字典表记录
     * @param id
     * @return BedDictionary
     */
    BedDictionary queryData(String id);

    /**
     * 更新一条床位字典表记录
     * @param bedDictionary
     * @return boolean
     */
    boolean update(BedDictionary bedDictionary);

    /**
     * 根据条件分页查询床位字典记录列表
     * @param map
     * @return list
     */
    List<BedDictionary> queryList(Map<String,Object> map);

    /**
     * 根据条件查询床位字典记录总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String,Object> map);

    /**
     * 查询所有床位列表
     * @param map
     * @return
     */
    List<BedDictionary> queryAllList(Map<String,Object> map);

    /**
     * 根据床位编码查询床位字典对象
     * @param map
     * @return
     */
    BedDictionary queryDataByBedCode(Map<String,Object> map);

    /**
     * 查询所有的字典数据 stats!=-1
     * @param map
     * @return
     */
     List<BedDictionary> queryBedList(Map<String,Object> map);

}
