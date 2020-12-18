package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.HandoverRecordDao;
import com.jyxd.web.data.basic.HandoverRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HandoverRecordService {

    @Autowired
    private HandoverRecordDao handoverRecordDao;

    public boolean insert(HandoverRecord handoverRecord){
        return handoverRecordDao.insert(handoverRecord);
    }

    public boolean update(HandoverRecord handoverRecord){
        return handoverRecordDao.update(handoverRecord);
    }

    public HandoverRecord queryData(String id){
        return handoverRecordDao.queryData(id);
    }

    public List<HandoverRecord> queryList(Map<String,Object> map){
        return handoverRecordDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return handoverRecordDao.queryNum(map);}

    /**
     * 根据交接班主键查询交接班记录列表
     * @param handoverId
     * @return
     */
    public List<HandoverRecord> queryListByHandoverId(String handoverId){
        return handoverRecordDao.queryListByHandoverId(handoverId);
    }
}
