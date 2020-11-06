package com.jyxd.web.dao.user;

import com.jyxd.web.data.user.Access;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AccessDao {

    /**
     * 新增一条角色权限表记录
     * @param access
     * @return boolean
     */
    boolean insert(Access access);

    /**
     * 根据主键id查询一条角色权限表记录
     * @param id
     * @return Role
     */
    Access queryData(String id);

    /**
     * 更新一条角色权限表记录
     * @param access
     * @return boolean
     */
    boolean update(Access access);

    /**
     * 根据条件分页查询角角色权限表记录列表
     * @param map
     * @return list
     */
    List<Access> queryList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据条件分页查询角角色权限表记录列表
     * @param map
     * @return list
     */
    List<Access> getList(Map<String, Object> map);
}
