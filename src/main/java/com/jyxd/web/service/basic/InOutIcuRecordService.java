package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.InOutIcuRecordDao;
import com.jyxd.web.data.basic.InOutIcuRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InOutIcuRecordService {

    @Autowired
    private InOutIcuRecordDao inOutIcuRecordDao;

    public boolean insert(InOutIcuRecord inOutIcuRecord){
        return inOutIcuRecordDao.insert(inOutIcuRecord);
    }

    public boolean update(InOutIcuRecord inOutIcuRecord){
        return inOutIcuRecordDao.update(inOutIcuRecord);
    }

    public InOutIcuRecord queryData(String id){
        return inOutIcuRecordDao.queryData(id);
    }

    public List<InOutIcuRecord> queryList(Map<String,Object> map){
        return inOutIcuRecordDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return inOutIcuRecordDao.queryNum(map);}

    /**
     * 根据条件查询病人出入科记录表对象
     * @param map
     * @return list
     */
    public List<InOutIcuRecord> geList(Map<String, Object> map){
        return inOutIcuRecordDao.geList(map);
    }
}
