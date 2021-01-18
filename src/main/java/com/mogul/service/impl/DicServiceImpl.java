package com.mogul.service.impl;

import com.mogul.mapper.DicTypeMapper;
import com.mogul.mapper.DicValueMapper;
import com.mogul.pojo.DicType;
import com.mogul.pojo.DicTypeExample;
import com.mogul.pojo.DicValue;
import com.mogul.pojo.DicValueExample;
import com.mogul.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    DicTypeMapper dicTypeMapper;
    @Autowired
    DicValueMapper dicValueMapper;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map=new HashMap<>();
        List<DicType> dicTypes=dicTypeMapper.selectByExample(new DicTypeExample());
        for(DicType dicType:dicTypes){
            DicValueExample dicValueExample=new DicValueExample();
            dicValueExample.setOrderByClause("orderNo");
            dicValueExample.createCriteria().andTypecodeEqualTo(dicType.getCode());
            List<DicValue> dicValues=dicValueMapper.selectByExample(dicValueExample);
            map.put(dicType.getCode(),dicValues);
        }
        return map;
    }
}
