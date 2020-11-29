package com.mogul.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogul.mapper.ActivityMapper;
import com.mogul.mapper.UserMapper;
import com.mogul.pojo.Activity;
import com.mogul.pojo.ActivityExample;
import com.mogul.pojo.User;
import com.mogul.pojo.UserExample;
import com.mogul.service.ActivityService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ActivityMapper activityMapper;

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
        ActivityExample.Criteria criteria= activityExample.createCriteria();
        if(activity.getName()!=null)
            criteria.andNameLike(activity.getName());
        if(activity.getStartdate()!=null)
            criteria.andStartdateLike(activity.getStartdate());
        if(activity.getEnddate()!=null)
            criteria.andEnddateLike(activity.getEnddate());
        List<Activity> activities= activityMapper.selectByExample(activityExample);
        if(activities.size()!=0) {
            for (Activity a:activities)
                a.setOwner(userMapper.selectByPrimaryKey(a.getOwner()).getName());
            int size=activities.size();
            if (activity.getOwner() != null)
            for (int o=0;o<size;o++)
                if (!activities.get(o).getOwner().contains(activity.getOwner()))
                    activities.remove(o);
            pageInfo=new PageInfo<>(activities);
        }
        return pageInfo;
    }
}
