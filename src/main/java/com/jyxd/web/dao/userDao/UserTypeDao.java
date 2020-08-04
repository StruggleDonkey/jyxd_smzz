package com.jyxd.web.dao.userDao;

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
}
