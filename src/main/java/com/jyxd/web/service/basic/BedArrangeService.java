package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.BedArrangeDao;
import com.jyxd.web.data.basic.BedArrange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("bedArrangeService")
@Transactional
public class BedArrangeService {

    @Autowired
    private BedArrangeDao bedArrangeDao;

    public boolean insert(BedArrange bedArrange) {
        return bedArrangeDao.insert(bedArrange);
    }

    public boolean update(BedArrange bedArrange) {
        return bedArrangeDao.update(bedArrange);
    }

    public BedArrange queryData(String id) {
        return bedArrangeDao.queryData(id);
    }

    public List<BedArrange> queryList(Map<String, Object> map) {
        return bedArrangeDao.queryList(map);
    }

    public int queryNum(Map<String, Object> map) {
        return bedArrangeDao.queryNum(map);
    }

    public List<Map<String, Object>> getBedArrangeList(Map<String, Object> map) {
        return bedArrangeDao.getBedArrangeList(map);
    }

    /**
     * 根据条件查询床位安排实体对象
     *
     * @param map
     * @return
     */
    public BedArrange queryDataByCode(Map<String, Object> map) {
        return bedArrangeDao.queryDataByCode(map);
    }

    /**
     * 根据床位号查询床位安排
     *
     * @param bedCode
     * @return
     */
    public BedArrange queryDataByBedCode(String bedCode) {
        return bedArrangeDao.queryDataByBedCode(bedCode);
    }

    /**
     * 根据患者id查询床位安排
     *
     * @param patientId
     * @return
     */
    public BedArrange queryDataByPatientId(String patientId) {
        return bedArrangeDao.queryDataByPatientId(patientId);
    }
}
