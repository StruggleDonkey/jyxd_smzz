package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.DiagnoseCoefficientDictionaryDao;
import com.jyxd.web.data.dictionary.DiagnoseCoefficientDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DiagnoseCoefficientDictionaryService {

    @Autowired
    private DiagnoseCoefficientDictionaryDao diagnoseCoefficientDictionaryDao;

    public boolean insert(DiagnoseCoefficientDictionary diagnoseCoefficientDictionary){
        return diagnoseCoefficientDictionaryDao.insert(diagnoseCoefficientDictionary);
    }

    public boolean update(DiagnoseCoefficientDictionary diagnoseCoefficientDictionary){
        return diagnoseCoefficientDictionaryDao.update(diagnoseCoefficientDictionary);
    }

    public DiagnoseCoefficientDictionary queryData(String id){
        return diagnoseCoefficientDictionaryDao.queryData(id);
    }

    public List<DiagnoseCoefficientDictionary> queryList(Map<String,Object> map){
        return diagnoseCoefficientDictionaryDao.queryList(map);
    }

    public DiagnoseCoefficientDictionary queryDataByName(Map<String,Object> map){
        return diagnoseCoefficientDictionaryDao.queryDataByName(map);
    }
}
