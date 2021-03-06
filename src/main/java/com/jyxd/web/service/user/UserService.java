package com.jyxd.web.service.user;

import com.jyxd.web.dao.user.UserDao;
import com.jyxd.web.data.user.User;
import com.jyxd.web.mapper.PatientTestDao;
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

    @Autowired
    private PatientTestDao patientTestDao;

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

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return userDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return userDao.getNum(map);}

    /**
     * 根据工号查询用户
     * @param map
     * @return
     */
    public User queryDataByWorkNumber(Map<String,Object> map){
        return userDao.queryDataByWorkNumber(map);
    }

    /**
     * 根据登录名查询用户
     * @param map
     * @return
     */
    public User queryDataByLoginName(Map<String,Object> map){
        return userDao.queryDataByLoginName(map);
    }

    /**
     * 查询所有用户 status！=-1
     * @param map
     * @return
     */
    public List<User> queryUserList(Map<String,Object> map){
        return userDao.queryUserList(map);
    }

    /**
     * 从his数据库视图中查询职工信息数据
     * @param map
     * @return list
     */
    public List<Map<String, Object>> getUserByHis(Map<String, Object> map){
        return patientTestDao.getUserByHis(map);
    }

}
