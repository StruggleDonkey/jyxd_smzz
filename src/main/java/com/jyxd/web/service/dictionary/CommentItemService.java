package com.jyxd.web.service.dictionary;

import com.jyxd.web.dao.dictionary.CommentItemDao;
import com.jyxd.web.data.dictionary.CommenItemDictionary;
import com.jyxd.web.mapper.PatientTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommentItemService {

    @Autowired
    private CommentItemDao commentItemDao;

    @Autowired
    private PatientTestDao patientTestDao;

    public boolean insert(CommenItemDictionary commenItemDictionary){
        return commentItemDao.insert(commenItemDictionary);
    }

    public boolean update(CommenItemDictionary commenItemDictionary){
        return commentItemDao.update(commenItemDictionary);
    }

    public CommenItemDictionary queryData(String id){
        return commentItemDao.queryData(id);
    }

    public List<CommenItemDictionary> queryList(Map<String,Object> map){
        return commentItemDao.queryList(map);
    }

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return commentItemDao.getList(map);
    }

    public int queryNum(Map<String,Object> map){
        return commentItemDao.queryNum(map);
    }

    /**
     * 根据type获取对象列表
     * @param map
     * @return
     */
    public List<Map<String,Object>> getCodeListByType(Map<String,Object> map){
        return commentItemDao.getCodeListByType(map);
    }

    /**
     * 根据codes 获取多个名称（以逗号分隔）
     * @param map
     * @return
     */
    public String getNamesByCodes(Map<String,Object> map){
        return commentItemDao.getNamesByCodes(map);
    }

    /**
     * 根据type 获取多个名称（以 空格分隔）
     * @param map
     * @return
     */
    public String getNamesByType(Map<String,Object> map){
        return commentItemDao.getNamesByType(map);
    }

    /**
     * 根据type查询对象列表
     * @return
     */
    public List<CommenItemDictionary> queryDataListByType(Map<String,Object> map){
        return commentItemDao.queryDataListByType(map);
    }

    /**
     * 从his系统视图查询用药分类字典
     * @return
     */
    public List<Map<String, Object>> getDrugTypeByHis(Map<String,Object> map){
        return patientTestDao.getDrugTypeByHis(map);
    }

    /**
     * 从his系统视图查询补液类型字典
     * @return
     */
    public List<Map<String, Object>> getOrderAttrByHis(Map<String,Object> map){
        return patientTestDao.getOrderAttrByHis(map);
    }

    /**
     * 从his系统视图查询 给药途径字典
     * @return
     */
    public List<Map<String, Object>> getUseModeByHis(Map<String,Object> map){
        return patientTestDao.getUseModeByHis(map);
    }

    /**
     * 从his系统视图查询 病情
     * @return
     */
    public List<Map<String, Object>> getIllnessStateByHis(Map<String,Object> map){
        return patientTestDao.getIllnessStateByHis(map);
    }

    /**
     * 从his系统视图查询 护理级别
     * @return
     */
    public List<Map<String, Object>> getNursingLevelByHis(Map<String,Object> map){
        return patientTestDao.getNursingLevelByHis(map);
    }

    /**
     * 从his系统视图查询 婚姻状况
     * @return
     */
    public List<Map<String, Object>> getMaritalStateByHis(Map<String,Object> map){
        return patientTestDao.getMaritalStateByHis(map);
    }
}
