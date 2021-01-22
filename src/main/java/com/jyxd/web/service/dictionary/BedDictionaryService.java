package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.BedDictionaryDao;
import com.jyxd.web.data.dictionary.BedDictionary;
import com.jyxd.web.mapper.PatientTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BedDictionaryService {

    @Autowired
    private BedDictionaryDao bedDictionaryDao;

    @Autowired
    private PatientTestDao patientTestDao;

    public boolean insert(BedDictionary bedDictionary){
        return bedDictionaryDao.insert(bedDictionary);
    }

    public boolean update(BedDictionary bedDictionary){
        return bedDictionaryDao.update(bedDictionary);
    }

    public BedDictionary queryData(String id){
        return bedDictionaryDao.queryData(id);
    }

    public List<BedDictionary> queryList(Map<String,Object> map){
        return bedDictionaryDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return bedDictionaryDao.queryNum(map);}

    public List<BedDictionary> queryAllList(Map<String,Object> map){
        return bedDictionaryDao.queryAllList(map);
    }

    /**
     * 根据床位编码查询床位字典对象
     * @param map
     * @return
     */
    public BedDictionary queryDataByBedCode(Map<String,Object> map){
        return bedDictionaryDao.queryDataByBedCode(map);
    }

    /**
     * 查询所有的字典数据 stats!=-1
     * @param map
     * @return
     */
    public List<BedDictionary> queryBedList(Map<String,Object> map){
        return bedDictionaryDao.queryBedList(map);
    }

    /**
     * 从his数据库视图中查询床位字典数据
     * @param map
     * @return list
     */
    public List<Map<String, Object>> getBedByHis(Map<String, Object> map){
        return patientTestDao.getBedByHis(map);
    }
}
