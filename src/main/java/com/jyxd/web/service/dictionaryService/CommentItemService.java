package com.jyxd.web.service.dictionaryService;

import com.jyxd.web.dao.dictionaryDao.CommentItemDao;
import com.jyxd.web.data.dictionary.CommenItemDictionary;
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
}
