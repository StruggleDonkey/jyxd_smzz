package com.jyxd.web.dao.userDao;

import com.jyxd.web.data.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {

    /**
     * 新增一条平台用户表记录
     * @param user
     * @return boolean
     */
    boolean insert(User user);

    /**
     * 根据主键id查询一条平台用户表记录
     * @param id
     * @return User
     */
    User queryData(String id);

    /**
     * 更新一条平台用户表记录
     * @param user
     * @return boolean
     */
    boolean update(User user);

    /**
     * 根据条件分页查询平台用户表记录列表
     * @param map
     * @return list
     */
    List<User> queryList(Map<String, Object> map);

    /**
     * 根据账号密码查询用户
     * @param map
     * @return
     */
    User queryUserByNameAndPassword(Map<String, Object> map);
}
