package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.SystemPropertiesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SystemPropertiesService {

    @Autowired
    private SystemPropertiesDao systemPropertiesDao;

    /**
     * 根据条件查询系统配置数据
     * @param map
     * @return
     */
    public List<String> querySystemProperties(Map<String,Object> map){
        return systemPropertiesDao.querySystemProperties(map);
    }

    /**
     * 护理文书--护理单--出量--查询所有出量类型
     * @param map remark=出量类型
     * @return
     */
    public List<Map<String,Object>> getOutTypeList(Map<String,Object> map){
        return systemPropertiesDao.getOutTypeList(map);
    }

}
