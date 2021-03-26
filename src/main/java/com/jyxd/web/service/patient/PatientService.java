package com.jyxd.web.service.patient;

import com.jyxd.web.dao.dictionary.DepartmentDictionaryDao;
import com.jyxd.web.dao.patient.PatientDao;
import com.jyxd.web.data.dictionary.DepartmentDictionary;
import com.jyxd.web.data.patient.Patient;
import com.jyxd.web.mapper.PatientTestDao;
import com.jyxd.web.util.FileUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("patientService")
@Transactional
public class PatientService {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private PatientTestDao patientTestDao;

    @Autowired
    private DepartmentDictionaryDao departmentDictionaryDao;

    public boolean insert(Patient patient){
        return patientDao.insert(patient);
    }

    public boolean update(Patient patient){
        return patientDao.update(patient);
    }

    public Patient queryData(String id){
        return patientDao.queryData(id);
    }

    public List<Patient> queryList(Map<String,Object> map){
        return patientDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return patientDao.queryNum(map);}

    public List<Map<String,Object>> getList(Map<String,Object> map){
        return patientDao.getList(map);
    }

    public int getNum(Map<String,Object> map){return patientDao.getNum(map);}

    public int getAllNum(){return patientDao.getAllNum();}

    public List<LinkedHashMap<String,Object>> getDownloadList(Map<String,Object> map){
        return patientDao.getDownloadList(map);
    }

    public void getExcel(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map=new HashMap<>();
        List<LinkedHashMap<String,Object>> withdrawVos = patientDao.getDownloadList(map);

        // 创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建表
        HSSFSheet sheet = workbook.createSheet("病人信息");
        // 创建行
        HSSFRow row = sheet.createRow(0);
        // 创建单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 表头
        String[] head = {"住院号", "姓名", "性别", "入科时间", "出科时间", "当前状态", "出科方式", "来源科室","去向科室","入科床号","住院时间",
                "是否非计划", "病情", "责任医生", "责任护士", "最新手术时间", "最新手术名称", "诊断", "过敏史", "阳性"};
        HSSFCell cell;
        // 设置表头
        for (int iHead = 0; iHead < head.length; iHead++) {
            cell = row.createCell(iHead);
            cell.setCellValue(head[iHead]);
            cell.setCellStyle(cellStyle);
        }
        // 设置表格内容
        for (int iBody = 0; iBody < withdrawVos.size(); iBody++) {
            row = sheet.createRow(iBody + 1);
            LinkedHashMap<String,Object> u = withdrawVos.get(iBody);
            String[] userArray = new String[20];
            userArray[0] = u.get("住院号").toString();
            userArray[1] = u.get("姓名").toString();
            userArray[2] = u.get("性别").toString();
            userArray[3] = u.get("入科时间").toString();
            userArray[4] = u.get("出科时间").toString();
            userArray[5] = u.get("当前状态").toString();
            userArray[6] = u.get("出科方式").toString();
            userArray[7] = u.get("来源科室").toString();
            userArray[8] = u.get("去向科室").toString();
            userArray[9] = u.get("入科床号").toString();
            userArray[10] = u.get("住院时间").toString();
            userArray[11] = u.get("是否非计划").toString();
            userArray[12] = u.get("病情").toString();
            userArray[13] = u.get("责任医生").toString();
            userArray[14] = u.get("责任护士").toString();
            userArray[15] = u.get("最新手术时间").toString();
            userArray[16] = u.get("最新手术名称").toString();
            userArray[17] = u.get("诊断").toString();
            userArray[18] = u.get("过敏史").toString();
            userArray[19] = u.get("阳性").toString();
            for (int iArray = 0; iArray < userArray.length; iArray++) {
                row.createCell(iArray).setCellValue(userArray[iArray]);
            }
        }
        // 生成Excel文件
        FileUtil.createFile(response, workbook);
    }

    public int getNowPatientNum(Map<String,Object> map){
        return patientDao.getNowPatientNum(map);
    }

    /**
     * 首页查询在科病人来源
     * @return
     */
    public JSONArray getPatientSource(){
        JSONArray array=new JSONArray();
        Map<String, Object> map=new HashMap<>();
        List<DepartmentDictionary> list=departmentDictionaryDao.queryDataList(map);
        int num=0;
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                JSONObject obj=new JSONObject();
                map.put("departmentCode",list.get(i).getDepartmentCode());
                num=patientDao.getNum(map);
                if(num!=0){
                    obj.put("value",num);
                    obj.put("name",list.get(i).getDepartmentName());
                    array.add(obj);
                }
            }
        }
        return array;
    }

    /**
     * 首页查询床位列表
     * @return
     */
    public List<Map<String,Object>> getBedPatientList(){
        return patientDao.getBedPatientList();
    }

    /**
     * 查询待分配的病人列表（是否分配床位）
     * @param map
     * @return
     */
    public List<Patient> getNoBedPatientList(Map<String,Object> map){
        return patientDao.getNoBedPatientList(map);
    }

    /**
     * 查询已出科的病人列表
     * @param map
     * @return
     */
    public List<Patient> getOutPatientList(Map<String,Object> map){
        return patientDao.getOutPatientList(map);
    }

    /**
     * 统计分析--出入科--转入转出分析
     * @param map
     * @return
     */
    public int getOutAndIn(Map<String,Object> map){
        return patientDao.getOutAndIn(map);
    }

    /**
     * 查询所有病人的入科科室分类名称
     * @return
     */
    public List<Map<String,Object>> getAllEnterDepartment(Map<String,Object> map){
        return patientDao.getAllEnterDepartment(map);
    }

    /**
     * 统计分析--出入科--转入或转出科室病人数量
     * @param map
     * @return
     */
    public int getEnterAndExitDepartment(Map<String,Object> map){
        return patientDao.getEnterAndExitDepartment(map);
    }

    /**
     * 查询所有病人的出科科室分类名称
     * @return
     */
    public List<Map<String,Object>> getAllExitDepartment(Map<String,Object> map){
        return patientDao.getAllExitDepartment(map);
    }

    /**
     * 统计分析--出入科--趋势分析--转出方式
     * @return
     */
    public List<Map<String,Object>> getNumByExitType(Map<String,Object> map){
        return patientDao.getNumByExitType(map);
    }

    /**
     * 统计分析--出入科--趋势分析--转出方式（按月）
     * @return
     */
    public List<Map<String,Object>> getNumByExitTypeMonth(Map<String,Object> map){
        return patientDao.getNumByExitTypeMonth(map);
    }

    /**
     * 模拟查询视图
     * @return
     */
    public List<Map<String,Object>> queryListTest(Map<String,Object> map){
        return patientTestDao.queryListTest(map);
    }

    /**
     * 查询所有病人信息 stats!=-1
     * @return
     */
    public List<Patient> queryPatientList(Map<String,Object> map){
        return patientDao.queryPatientList(map);
    }

    /**
     * 从his系统视图查询所有病人信息
     * @return
     */
    public List<Map<String,Object>> getPatientByHis(Map<String,Object> map){
        return patientTestDao.getPatientByHis(map);
    }

    /**
     * 根据条件查询病人对象
     * @param map
     * @return
     */
    public Patient getPatientByConditions(Map<String,Object> map){
        return patientDao.getPatientByConditions(map);
    }

    /**
     * 从his系统视图查询所有病人转移信息
     * @return
     */
    public List<Map<String, Object>> getTransferByHis(Map<String,Object> map){
        return patientTestDao.getTransferByHis(map);
    }

    /**
     * 病人管理--首页--查询病人监护仪及采集频率
     * @param map
     * @return
     */
    public Map<String, Object> queryPatientMonitor(Map<String,Object> map){
        return patientDao.queryPatientMonitor(map);
    }
}
