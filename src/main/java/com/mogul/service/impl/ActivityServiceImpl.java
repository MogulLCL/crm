package com.mogul.service.impl;

import com.mogul.mapper.ActivityMapper;
import com.mogul.pojo.Activity;
import com.mogul.service.ActivityService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public int save(Activity activity) {
        activity.setId(UUIDUtil.getUUID());
        activity.setCreatetime(DateTimeUtil.getSysTime());
        activity.setEditby("");activity.setEdittime("");
        return activityMapper.insertSelective(activity);
    }
}
