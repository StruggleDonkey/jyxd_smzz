package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.SkinNursing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SkinNursingDao {

    /**
     * 新增一条皮肤护理表记录
     * @param skinNursing
     * @return boolean
     */
    boolean insert(SkinNursing skinNursing);

    /**
     * 根据主键id查询一条皮肤护理表记录
     * @param id
     * @return SkinNursing
     */
    SkinNursing queryData(String id);

    /**
     * 更新一条皮肤护理表记录
     * @param skinNursing
     * @return boolean
     */
    boolean update(SkinNursing skinNursing);

    /**
     * 根据条件分页查询皮肤护理表记录列表
     * @param map
     * @return list
     */
    List<SkinNursing> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
