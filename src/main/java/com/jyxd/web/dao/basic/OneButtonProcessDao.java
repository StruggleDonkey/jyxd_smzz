package com.jyxd.web.dao.basic;

import com.jyxd.web.data.basic.OneButtonProcess;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OneButtonProcessDao {

    /**
     * 新增一条一键处理报警信息表记录
     * @param oneButtonProcess
     * @return boolean
     */
    boolean insert(OneButtonProcess oneButtonProcess);

    /**
     * 根据主键id查询一条一键处理报警信息表记录
     * @param id
     * @return OneButtonProcess
     */
    OneButtonProcess queryData(String id);

    /**
     * 更新一条一键处理报警信息表记录
     * @param oneButtonProcess
     * @return boolean
     */
    boolean update(OneButtonProcess oneButtonProcess);

    /**
     * 根据条件分页查询一键处理报警信息表记录列表
     * @param map
     * @return list
     */
    List<OneButtonProcess> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);
}
