package com.mogul.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogul.mapper.ClueMapper;
import com.mogul.mapper.UserMapper;
import com.mogul.pojo.Activity;
import com.mogul.pojo.Clue;
import com.mogul.pojo.ClueExample;
import com.mogul.service.ClueService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    ClueMapper clueMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public int save(Clue clue) {
        clue.setId(UUIDUtil.getUUID());
        clue.setCreatetime(DateTimeUtil.getSysTime());
        return clueMapper.insertSelective(clue);
    }

    @Override
    public List<Activity> getClueAndActivity(String id) {
        return clueMapper.selectByActivity(id);
    }

    @Override
    public PageInfo pageList(Integer pageno, Integer pagesize, Clue clue) {
        PageHelper.startPage(pageno,pagesize);
        ClueExample clueExample=new ClueExample();
        clueExample.setOrderByClause("a.createTime DESC");
        PageInfo pageInfo=new PageInfo<>(clueMapper.selectByJoin(clueExample));
        return pageInfo;
    }

    @Override
    public PageInfo pageList1(Integer pageno, Integer pagesize, Clue clue) {
        PageHelper.startPage(pageno,pagesize);
        ClueExample clueExample=new ClueExample();
        clueExample.setOrderByClause("createTime DESC");
        List<Clue> clues=clueMapper.selectByExample(clueExample);
        for(Clue c:clues)
            c.setOwner(userMapper.selectByPrimaryKey(c.getOwner()).getName());
        PageInfo pageInfo=new PageInfo<>(clues);
        return pageInfo;
    }

    @Override
    public Map<String, Object> getUSerAndClue(String id) {
        Map<String,Object> map=new HashMap<>();
        map.put("u",userMapper.selectByName());
        map.put("c",clueMapper.selectByPrimaryKey(id));
        return map;
    }

    @Override
    public int delete(String id) {
        return clueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Clue getDetail(String id) {
        Clue clue=clueMapper.selectByPrimaryKey(id);
        if(clue!=null)clue.setOwner(userMapper.selectByPrimaryKey(clue.getOwner()).getName());
        return clue;
    }
}
