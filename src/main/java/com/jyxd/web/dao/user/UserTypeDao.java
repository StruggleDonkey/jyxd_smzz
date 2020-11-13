package com.jyxd.web.dao.user;

import com.jyxd.web.data.user.UserType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserTypeDao {

    /**
     * 新增一条平台用户类型表记录
     * @param userType
     * @return boolean
     */
    boolean insert(UserType userType);

    /**
     * 根据主键id查询一条平平台用户类型表记录
     * @param id
     * @return UserType
     */
    UserType queryData(String id);

    /**
     * 更新一条平台用户类型表记录
     * @param userType
     * @return boolean
     */
    boolean update(UserType userType);

    /**
     * 根据条件分页查询平台用户类型表记录列表
     * @param map
     * @return list
     */
    List<UserType> queryList(Map<String, Object> map);

    /**
     * 根据用户类型code和用户类型名称查询对象
     * @param map
     * @return
     */
    UserType queryDataByCode(Map<String, Object> map);

    /**
     * 根据条件查询列表总记录数
     * @param map
     * @return
     */
    int queryNum(Map<String, Object> map);

    /**
     * 根据用户类型名称 查询用户类型
     * @param map
     */
    UserType queryDataByName(Map<String, Object> map);

}
