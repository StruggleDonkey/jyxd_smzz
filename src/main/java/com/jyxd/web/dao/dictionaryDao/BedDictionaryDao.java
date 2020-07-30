package com.jyxd.web.dao.dictionaryDao;

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
}
