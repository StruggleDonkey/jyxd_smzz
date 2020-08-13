package com.jyxd.web.service.user;

import com.jyxd.web.dao.user.UserTypeDao;
import com.jyxd.web.data.user.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserTypeService {

    @Autowired
    private UserTypeDao userTypeDao;

    public boolean insert(UserType userType){
        return userTypeDao.insert(userType);
    }

    public boolean update(UserType userType){
        return userTypeDao.update(userType);
    }

    public UserType queryData(String id){
        return userTypeDao.queryData(id);
    }

    public List<UserType> queryList(Map<String,Object> map){
        return userTypeDao.queryList(map);
    }

    public UserType queryDataByCode(Map<String,Object> map){
        return userTypeDao.queryDataByCode(map);
    }

    public int queryNum(Map<String,Object> map){return userTypeDao.queryNum(map);}
}
