package com.jyxd.web.service.user;

import com.jyxd.web.dao.user.RoleDao;
import com.jyxd.web.data.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public boolean insert(Role role){
        return roleDao.insert(role);
    }

    public boolean update(Role role){
        return roleDao.update(role);
    }

    public Role queryData(String id){
        return roleDao.queryData(id);
    }

    public List<Role> queryList(Map<String,Object> map){
        return roleDao.queryList(map);
    }

    public Role queryDataByName(Map<String,Object> map){
        return roleDao.queryDataByName(map);
    }

    public int queryNum(Map<String,Object> map){return roleDao.queryNum(map);}

    public Role queryDataByTypeCode(Map<String,Object> map){
        return roleDao.queryDataByTypeCode(map);
    }

}
