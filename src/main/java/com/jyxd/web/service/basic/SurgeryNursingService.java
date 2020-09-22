package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.SurgeryNursingDao;
import com.jyxd.web.data.basic.SurgeryNursing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SurgeryNursingService {

    @Autowired
    private SurgeryNursingDao surgeryNursingDao;

    public boolean insert(SurgeryNursing surgeryNursing){
        return surgeryNursingDao.insert(surgeryNursing);
    }

    public boolean update(SurgeryNursing surgeryNursing){
        return surgeryNursingDao.update(surgeryNursing);
    }

    public SurgeryNursing queryData(String id){
        return surgeryNursingDao.queryData(id);
    }

    public List<SurgeryNursing> queryList(Map<String,Object> map){
        return surgeryNursingDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return surgeryNursingDao.queryNum(map);}
}
