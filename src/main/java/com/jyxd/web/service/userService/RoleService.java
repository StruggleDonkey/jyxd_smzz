package com.jyxd.web.service.userService;

import com.jyxd.web.dao.userDao.RoleDao;
import com.jyxd.web.data.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
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

}
