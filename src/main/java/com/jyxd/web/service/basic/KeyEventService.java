package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.KeyEventDao;
import com.jyxd.web.data.basic.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class KeyEventService {

    @Autowired
    private KeyEventDao keyEventDao;

    public boolean insert(KeyEvent keyEvent){
        return keyEventDao.insert(keyEvent);
    }

    public boolean update(KeyEvent keyEvent){
        return keyEventDao.update(keyEvent);
    }

    public KeyEvent queryData(String id){
        return keyEventDao.queryData(id);
    }

    public List<KeyEvent> queryList(Map<String,Object> map){
        return keyEventDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return keyEventDao.queryNum(map);}
}
