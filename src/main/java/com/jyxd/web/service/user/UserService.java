package com.jyxd.web.service.user;

import com.jyxd.web.dao.user.UserDao;
import com.jyxd.web.data.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean insert(User user){
        return userDao.insert(user);
    }

    public boolean update(User user){
        return userDao.update(user);
    }

    public User queryData(String id){
        return userDao.queryData(id);
    }

    public List<User> queryList(Map<String,Object> map){
        return userDao.queryList(map);
    }

    public User queryUserByNameAndPassword(Map<String,Object> map){
        return userDao.queryUserByNameAndPassword(map);
    }

    public int queryNum(Map<String,Object> map){return userDao.queryNum(map);}

    /**
     * 床位信息--新增病人--根据条件查询医生或护士员工列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getUserListByType(Map<String,Object> map){
        return userDao.getUserListByType(map);
    }
}
