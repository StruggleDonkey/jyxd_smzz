package com.jyxd.web.dao.patientDao;

import com.jyxd.web.data.patient.PatientLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatientLogDao {

    /**
     * 新增一条病人表日志记录
     * @param patientLog
     * @return boolean
     */
    boolean insert(PatientLog patientLog);

    /**
     * 根据主键id查询一条病人日志表记录
     * @param id
     * @return PatientLog
     */
    PatientLog queryData(String id);

    /**
     * 更新一条病人日志表记录
     * @param patientLog
     * @return boolean
     */
    boolean update(PatientLog patientLog);

    /**
     * 根据条件分页查询病人日志表记录列表
     * @param map
     * @return list
     */
    List<PatientLog> queryList(Map<String, Object> map);
}
