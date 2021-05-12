package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BasicNursingDao;
import com.jyxd.web.dao.basic.CustomFieldDao;
import com.jyxd.web.data.basic.BasicNursing;
import com.jyxd.web.data.basic.CustomField;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomFieldService {

    @Autowired
    private CustomFieldDao customFieldDao;

    public boolean insert(CustomField customField){
        return customFieldDao.insert(customField);
    }

    public boolean update(CustomField customField){
        return customFieldDao.update(customField);
    }

    public CustomField queryData(String id){
        return customFieldDao.queryData(id);
    }

    public List<CustomField> queryList(Map<String,Object> map){
        return customFieldDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return customFieldDao.queryNum(map);}

}
