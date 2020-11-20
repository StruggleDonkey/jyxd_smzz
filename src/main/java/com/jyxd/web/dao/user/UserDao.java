package com.jyxd.web.dao.user;

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

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     *床位信息--新增病人--根据条件查询医生或护士员工列表
     * @param map
     * @return
     */
    List<Map<String,Object>> getUserListByType(Map<String, Object> map);

    /**
     * 根据条件分页查询平台用户表记录列表--多表查询
     * @param map
     * @return list
     */
    List<Map<String, Object>> getList(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数--多表查询
     * @param map
     * @return
     */
    int getNum(Map<String, Object> map);

    /**
     * 根据工号查询用户
     * @param map
     * @return
     */
    User queryDataByWorkNumber(Map<String, Object> map);

    /**
     * 根据登录名查询用户
     * @param map
     * @return
     */
    User queryDataByLoginName(Map<String,Object> map);
}
