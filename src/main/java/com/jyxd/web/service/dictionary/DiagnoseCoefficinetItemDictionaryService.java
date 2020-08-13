package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.DiagnoseCoefficinetItemDictionaryDao;
import com.jyxd.web.data.dictionary.DiagnoseCoefficinetItemDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
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

    public DiagnoseCoefficinetItemDictionary queryDataByName(Map<String,Object> map){
        return diagnoseCoefficinetItemDictionaryDao.queryDataByName(map);
    }

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return diagnoseCoefficinetItemDictionaryDao.getList(map);
    }

    public int queryNum(Map<String,Object> map){return diagnoseCoefficinetItemDictionaryDao.queryNum(map);}
}
