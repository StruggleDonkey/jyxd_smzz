package com.jyxd.web.dao.userDao;

import com.jyxd.web.data.user.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao {

    /**
     * 新增一条角色表记录
     * @param role
     * @return boolean
     */
    boolean insert(Role role);

    /**
     * 根据主键id查询一条角色表记录
     * @param id
     * @return Role
     */
    Role queryData(String id);

    /**
     * 更新一条角色表记录
     * @param role
     * @return boolean
     */
    boolean update(Role role);

    /**
     * 根据条件分页查询角色表记录列表
     * @param map
     * @return list
     */
    List<Role> queryList(Map<String, Object> map);
}
