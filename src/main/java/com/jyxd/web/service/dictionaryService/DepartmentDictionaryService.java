package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.DepartmentDictionaryDao;
import com.jyxd.web.data.dictionary.DepartmentDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DepartmentDictionaryService {

    @Autowired
    private DepartmentDictionaryDao departmentDictionaryDao;

    public boolean insert(DepartmentDictionary departmentDictionary){
        return departmentDictionaryDao.insert(departmentDictionary);
    }

    public boolean update(DepartmentDictionary departmentDictionary){
        return departmentDictionaryDao.update(departmentDictionary);
    }

    public DepartmentDictionary queryData(String id){
        return departmentDictionaryDao.queryData(id);
    }

    public List<DepartmentDictionary> queryList(Map<String,Object> map){
        return departmentDictionaryDao.queryList(map);
    }
}
