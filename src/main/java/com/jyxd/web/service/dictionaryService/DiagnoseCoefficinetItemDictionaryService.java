package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.DiagnoseCoefficinetItemDictionaryDao;
import com.jyxd.web.data.dictionary.DiagnoseCoefficinetItemDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DiagnoseCoefficinetItemDictionaryService {

    @Autowired
    private DiagnoseCoefficinetItemDictionaryDao diagnoseCoefficinetItemDictionaryDao;

    public boolean insert(DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary){
        return diagnoseCoefficinetItemDictionaryDao.insert(diagnoseCoefficinetItemDictionary);
    }

    public boolean update(DiagnoseCoefficinetItemDictionary diagnoseCoefficinetItemDictionary){
        return diagnoseCoefficinetItemDictionaryDao.update(diagnoseCoefficinetItemDictionary);
    }

    public DiagnoseCoefficinetItemDictionary queryData(String id){
        return diagnoseCoefficinetItemDictionaryDao.queryData(id);
    }

    public List<DiagnoseCoefficinetItemDictionary> queryList(Map<String,Object> map){
        return diagnoseCoefficinetItemDictionaryDao.queryList(map);
    }
}
