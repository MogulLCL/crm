package com.mogul.service.impl;

import com.mogul.mapper.TranHistoryMapper;
import com.mogul.pojo.TranHistory;
import com.mogul.pojo.TranHistoryExample;
import com.mogul.service.TranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TranHistoryServiceImpl implements TranHistoryService {
    @Autowired
    TranHistoryMapper tranHistoryMapper;

    @Override
    public List<TranHistory> selectTranHistoryByTranId(String id) {
        TranHistoryExample tranHistoryExample=new TranHistoryExample();
        tranHistoryExample.setOrderByClause("createtime DESC");
        tranHistoryExample.createCriteria().andTranidEqualTo(id);
        List<TranHistory> tranHistories=tranHistoryMapper.selectByExample(tranHistoryExample);
        return tranHistories;
    }

    @Override
    public Map<String, Object> stageCount() {
        List<Map<String, Object>> mapList=tranHistoryMapper.stageCount();
        Map<String, Object> map=new HashMap<>();
        Integer total=0;
        Iterator<Map<String, Object>> iterator=mapList.iterator();
        Set<String> strings=new HashSet<>();
        while (iterator.hasNext()) {
            Map<String, Object> a = iterator.next();
            total += Integer.parseInt(a.get("value").toString());
            strings.add(a.get("name").toString());
        }
        map.put("total",total);
        map.put("datalist",mapList);
        map.put("title",strings);
        System.out.println(map);
        return map;
    }
}
