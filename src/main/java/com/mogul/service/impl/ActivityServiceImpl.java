package com.mogul.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogul.mapper.ActivityMapper;
import com.mogul.mapper.UserMapper;
import com.mogul.pojo.Activity;
import com.mogul.pojo.ActivityExample;
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
        activityExample.setOrderByClause("createtime DESC");
        ActivityExample.Criteria criteria= activityExample.createCriteria();
        if(activity.getName()!=null&&!"".equals(activity.getName()))
            criteria.andNameLike("%"+activity.getName()+"%");
        if(activity.getStartdate()!=null&&!"".equals(activity.getStartdate()))
            criteria.andStartdateGreaterThanOrEqualTo(activity.getStartdate());
        if(activity.getEnddate()!=null&&!"".equals(activity.getEnddate()))
            criteria.andEnddateLessThanOrEqualTo(activity.getEnddate());
        List<Activity> activities= activityMapper.selectByExample(activityExample);
        if(activities.size()!=0) {
            for (Activity a:activities)
                a.setOwner(userMapper.selectByPrimaryKey(a.getOwner()).getName());
            if (activity.getOwner() != null&&!"".equals(activity.getOwner()))
            for (int o=0;o<activities.size();o++)
                if (!activities.get(o).getOwner().contains(activity.getOwner()))
                    activities.remove(o--);
            pageInfo=new PageInfo<>(activities);
            System.out.println(pageInfo);
        }
        return pageInfo;
    }


}
