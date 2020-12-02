package com.mogul.service;

import com.github.pagehelper.PageInfo;
import com.mogul.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int save(Activity activity);
    PageInfo pageList(Integer pageNo,Integer pageSize,Activity activity);
    int delete(String id);
    int edit(Activity activity);
    Map<String,Object> getUserAndActivity(String id);
}
