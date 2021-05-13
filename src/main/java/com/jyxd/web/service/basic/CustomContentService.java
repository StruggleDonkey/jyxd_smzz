package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.CustomContentDao;
import com.jyxd.web.dao.basic.CustomFieldDao;
import com.jyxd.web.data.basic.CustomContent;
import com.jyxd.web.data.basic.CustomField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomContentService {

    @Autowired
    private CustomContentDao customContentDao;

    public boolean insert(CustomContent customContent){
        return customContentDao.insert(customContent);
    }

    public boolean update(CustomContent customContent){
        return customContentDao.update(customContent);
    }

    public CustomContent queryData(String id){
        return customContentDao.queryData(id);
    }

    public List<CustomContent> queryList(Map<String,Object> map){
        return customContentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return customContentDao.queryNum(map);}

    /**
     * 根据时间 病人主键id 表名 查询对象
     * @param map
     * @return
     */
    public List<CustomContent> getCustomContentByTime(Map<String, Object> map){
        return customContentDao.getCustomContentByTime(map);
    }
}
