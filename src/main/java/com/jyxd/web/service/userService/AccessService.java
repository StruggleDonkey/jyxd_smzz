package com.jyxd.web.service.userService;

import com.jyxd.web.dao.userDao.AccessDao;
import com.jyxd.web.data.user.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccessService {

    @Autowired
    private AccessDao accessDao;

    public boolean insert(Access access){
        return accessDao.insert(access);
    }

    public boolean update(Access access){
        return accessDao.update(access);
    }

    public Access queryData(String id){
        return accessDao.queryData(id);
    }

    public List<Access> queryList(Map<String,Object> map){
        return accessDao.queryList(map);
    }

}
