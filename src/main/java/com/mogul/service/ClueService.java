package com.mogul.service;

import com.github.pagehelper.PageInfo;
import com.mogul.pojo.Activity;
import com.mogul.pojo.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    int save(Clue clue);
    PageInfo pageList(Integer pageno, Integer pagesize, Clue clue);
    PageInfo pageList1(Integer pageno, Integer pagesize, Clue clue);
    Clue getDetail(String id);
    int delete(String id);
    Map<String, Object> getUSerAndClue(String id);
    List<Activity> getClueAndActivity(String id);
}
