package com.mogul.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogul.mapper.ActivityMapper;
import com.mogul.mapper.ActivityRemarkMapper;
import com.mogul.mapper.UserMapper;
import com.mogul.pojo.*;
import com.mogul.service.ActivityService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    ActivityRemarkMapper activityRemarkMapper;

    @Override
    public int save(Activity activity) {
        activity.setId(UUIDUtil.getUUID());
        activity.setCreatetime(DateTimeUtil.getSysTime());
        activity.setEditby("");activity.setEdittime("");
        return activityMapper.insertSelective(activity);
    }

    @Override
    public PageInfo pageList(Integer pageNo, Integer pageSize, Activity activity) {
        PageHelper.startPage(pageNo,pageSize);
        PageInfo pageInfo=null;
        ActivityExample activityExample=new ActivityExample();
        activityExample.setOrderByClause("createtime DESC");
        ActivityExample.Criteria criteria= activityExample.createCriteria();
        if(activity.getName()!=null&&!"".equals(activity.getName()))
            criteria.andNameLike("%"+activity.getName()+"%");
        if (activity.getOwner() != null&&!"".equals(activity.getOwner()))
            criteria.andOwnerLike("%"+activity.getOwner()+"%");
        if(activity.getStartdate()!=null&&!"".equals(activity.getStartdate()))
            criteria.andStartdateGreaterThanOrEqualTo(activity.getStartdate());
        if(activity.getEnddate()!=null&&!"".equals(activity.getEnddate()))
            criteria.andEnddateLessThanOrEqualTo(activity.getEnddate());
        List<Activity> activities= activityMapper.selectJoin(activityExample);
        pageInfo=new PageInfo<>(activities);
        return pageInfo;
    }


    @Override
    public int delete(String id) {
        activityRemarkMapper.deleteByActivityId(id);
        for(String ids:id.split(","))
            activityMapper.deleteByPrimaryKey(ids);
        return 1;
    }

    @Override
    public int edit(Activity activity) {
        activity.setEdittime(DateTimeUtil.getSysTime());
        return activityMapper.updateByPrimaryKeySelective(activity);
    }

    @Override
    public Map<String, Object> getUserAndActivity(String id) {
        Map<String,Object> map=new HashMap<>();
        map.put("uList",userMapper.selectByName());
        map.put("a",activityMapper.selectByPrimaryKey(id));
        return map;
    }

    @Override
    public Activity getDetail(String id) {
        Activity activity=activityMapper.selectByPrimaryKey(id);
        if(activity!=null)
        activity.setOwner(userMapper.selectByPrimaryKey(activity.getOwner()).getName());
        return activity;
    }

    @Override
    public List<ActivityRemark> getActivityRemark(String id) {
        ActivityRemarkExample activityRemarkExample=new ActivityRemarkExample();
        activityRemarkExample.createCriteria().andActivityidEqualTo(id);
        return activityRemarkMapper.selectByExample(activityRemarkExample);
    }

    @Override
    public int deleteActivityRemark(String id) {
        return activityRemarkMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ActivityRemark addActivityRemark(ActivityRemark activityRemark) {
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreatetime(DateTimeUtil.getSysTime());
        activityRemark.setEditflag("0");
        activityRemarkMapper.insertSelective(activityRemark);
        return activityRemark;
    }

    @Override
    public ActivityRemark updateActivityRemark(ActivityRemark activityRemark) {
        activityRemark.setEditflag("1");
        activityRemark.setEdittime(DateTimeUtil.getSysTime());
        activityRemarkMapper.updateByPrimaryKeySelective(activityRemark);
        return activityRemark;
    }
}
