package com.jyxd.web.service.user;

import com.jyxd.web.dao.user.AccessDao;
import com.jyxd.web.data.user.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
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

    public int queryNum(Map<String,Object> map){return accessDao.queryNum(map);}

}
