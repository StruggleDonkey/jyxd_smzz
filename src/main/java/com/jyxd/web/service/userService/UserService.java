package com.jyxd.web.service.userService;

import com.jyxd.web.dao.userDao.UserDao;
import com.jyxd.web.data.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
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
}
