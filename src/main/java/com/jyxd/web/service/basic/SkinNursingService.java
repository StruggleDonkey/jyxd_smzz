package com.jyxd.web.service.basic;

import com.jyxd.web.dao.basic.SkinNursingDao;
import com.jyxd.web.data.basic.SkinNursing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SkinNursingService {

    @Autowired
    private SkinNursingDao skinNursingDao;

    public boolean insert(SkinNursing skinNursing){
        return skinNursingDao.insert(skinNursing);
    }

    public boolean update(SkinNursing skinNursing){
        return skinNursingDao.update(skinNursing);
    }

    public SkinNursing queryData(String id){
        return skinNursingDao.queryData(id);
    }

    public List<SkinNursing> queryList(Map<String,Object> map){
        return skinNursingDao.queryList(map);
    }

    public int queryNum(Map<String,Object> map){return skinNursingDao.queryNum(map);}
}
