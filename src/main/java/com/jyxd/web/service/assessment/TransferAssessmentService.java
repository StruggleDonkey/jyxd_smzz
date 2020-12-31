package com.jyxd.web.service.assessment;

import com.jyxd.web.dao.assessment.TransferAssessmentDao;
import com.jyxd.web.data.assessment.TransferAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransferAssessmentService {

    @Autowired
    private TransferAssessmentDao transferAssessmentDao;

    public boolean insert(TransferAssessment transferAssessment){
        return transferAssessmentDao.insert(transferAssessment);
    }

    public boolean update(TransferAssessment transferAssessment){
        return transferAssessmentDao.update(transferAssessment);
    }

    public TransferAssessment queryData(String id){
        return transferAssessmentDao.queryData(id);
    }

    public List<TransferAssessment> queryList(Map<String,Object> map){
        return transferAssessmentDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return transferAssessmentDao.queryNum(map);}

    /**
     * 根据评估主键查询对象列表
     * @param map
     * @return
     */
    public List<TransferAssessment> getListByAssessmentIdAndPatientId(Map<String,Object> map){
        return transferAssessmentDao.getListByAssessmentIdAndPatientId(map);
    }

    /**
     * 根据记录时间查询对象列表
     * @param map
     * @return
     */
    public List<TransferAssessment> getListByDataTimeAndPatientId(Map<String,Object> map){
        return transferAssessmentDao.getListByDataTimeAndPatientId(map);
    }

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return transferAssessmentDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return transferAssessmentDao.getNum(map);}

    /**
     * 护理评估--入院护理评估单--历史列表--选择
     * @param map
     * @return
     */
    public Map<String,Object> getDataByAssessmentId(Map<String,Object> map){
        return transferAssessmentDao.getDataByAssessmentId(map);
    }
}
