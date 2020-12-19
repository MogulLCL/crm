package com.mogul.service.impl;

import com.mogul.mapper.ClueMapper;
import com.mogul.pojo.Clue;
import com.mogul.service.ClueService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    ClueMapper clueMapper;

    @Override
    public int save(Clue clue) {
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateby(DateTimeUtil.getSysTime());
        return clueMapper.insertSelective(clue);
    }
}
