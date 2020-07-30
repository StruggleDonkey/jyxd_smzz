package com.jyxd.web.service;

import com.jyxd.web.dao.UserDao;
import com.jyxd.web.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean insert(User user){
        return userDao.insert(user);
    }
}
