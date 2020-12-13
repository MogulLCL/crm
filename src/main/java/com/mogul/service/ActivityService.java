package com.mogul.service;

import com.github.pagehelper.PageInfo;
import com.mogul.pojo.Activity;
import com.mogul.pojo.ActivityRemark;


import java.util.List;
import java.util.Map;

public interface ActivityService {
    int save(Activity activity);
    PageInfo pageList(Integer pageNo,Integer pageSize,Activity activity);
    int delete(String id);
    int edit(Activity activity);
    Map<String,Object> getUserAndActivity(String id);
    Activity getDetail(String id);
    List<ActivityRemark> getActivityRemark(String id);
    int deleteActivityRemark(String id);
    ActivityRemark addActivityRemark(ActivityRemark activityRemark);
    ActivityRemark updateActivityRemark(ActivityRemark activityRemark);
}
