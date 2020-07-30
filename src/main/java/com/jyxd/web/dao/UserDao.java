package com.jyxd.web.dao;

import com.jyxd.web.data.User;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    boolean insert(User user);
}
