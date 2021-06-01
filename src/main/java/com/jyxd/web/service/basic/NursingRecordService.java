package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.NursingRecordDao;
import com.jyxd.web.data.basic.NursingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NursingRecordService {

    @Autowired
    private NursingRecordDao nursingRecordDao;

    public boolean insert(NursingRecord nursingRecord) {
        return nursingRecordDao.insert(nursingRecord);
    }

    public boolean update(NursingRecord nursingRecord) {
        return nursingRecordDao.update(nursingRecord);
    }

    public NursingRecord queryData(String id) {
        return nursingRecordDao.queryData(id);
    }

    public List<NursingRecord> queryList(Map<String, Object> map) {
        return nursingRecordDao.queryList(map);
    }

    public int queryNum(Map<String, Object> map) {
        return nursingRecordDao.queryNum(map);
    }

    /**
     * 快捷录入--护理单--查询在科病人的护理信息
     *
     * @param map
     * @return
     */
    public List<Map<String, Object>> getListByTime(Map<String, Object> map) {
        return nursingRecordDao.getListByTime(map);
    }

    /**
     * 根据时间和code查询护理对象
     *
     * @param map
     * @return
     */
    public NursingRecord queryDataByTimeAndCode(Map<String, Object> map) {
        return nursingRecordDao.queryDataByTimeAndCode(map);
    }

    /**
     * 护理文书--护理单--护理记录--查询护理记录列表
     *
     * @param map code=score
     * @return
     */
    public List<Map<String, Object>> getListByCode(Map<String, Object> map) {
        return nursingRecordDao.getListByCode(map);
    }

    public List<Map<String, Object>> getListByStartTime(Map<String, Object> map) {
        return nursingRecordDao.getListByStartTime(map);
    }
}
