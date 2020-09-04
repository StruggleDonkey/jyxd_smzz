package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.BasicNursing;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BasicNursingDao {

    /**
     * 新增一条基础护理表记录
     * @param basicNursing
     * @return boolean
     */
    boolean insert(BasicNursing basicNursing);

    /**
     * 根据主键id查询一条基础护理表记录
     * @param id
     * @return BasicNursing
     */
    BasicNursing queryData(String id);

    /**
     * 更新一条基础护理表记录
     * @param basicNursing
     * @return boolean
     */
    boolean update(BasicNursing basicNursing);

    /**
     * 根据条件分页查询基础护理表记录列表
     * @param map
     * @return list
     */
    List<BasicNursing> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
