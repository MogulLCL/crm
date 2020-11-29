package com.mogul.service;

import com.github.pagehelper.PageInfo;
import com.mogul.pojo.Activity;

public interface ActivityService {
    int save(Activity activity);
    PageInfo pageList(Integer pageNo,Integer pageSize,Activity activity);
}
