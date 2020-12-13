package com.mogul.controller.workbench;

import com.mogul.domian.Result;
import com.mogul.pojo.Activity;
import com.mogul.pojo.ActivityRemark;
import com.mogul.pojo.User;
import com.mogul.service.ActivityService;
import com.mogul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("workbench/activity")
public class ActivityController {
    @Autowired
    UserService userService;
    @Autowired
    ActivityService activityService;


    @RequestMapping(value = "getuser",method = RequestMethod.GET)
    public Result getUser(){
        return Result.success(userService.getName());
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result save(Activity activity, HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        activity.setCreateby(user.getName());
        return Result.success(activityService.save(activity));
    }

    @RequestMapping(value = "pagelist",method = RequestMethod.GET)
    public Result pageList(@RequestParam(value = "pageno",defaultValue = "1") Integer pageno,
                           @RequestParam(value = "pagesize",defaultValue = "5") Integer pagesize,
                           Activity activity){
        return Result.success(activityService.pageList(pageno,pagesize,activity));
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Result delete(String id){
      return Result.success(activityService.delete(id));
    }

    @RequestMapping(value = "getuserandactivity",method = RequestMethod.GET)
    public Result getUserAndActivity(String id){
        return Result.success(activityService.getUserAndActivity(id));
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public Result edit(Activity activity,HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        activity.setEditby(user.getName());
        return Result.success(activityService.edit(activity));
    }

    @RequestMapping(value = "getdetail",method = RequestMethod.GET)
    public Result getDetail(String id){
        return Result.success(activityService.getDetail(id));
    }

    @RequestMapping(value = "getactivityremark",method = RequestMethod.GET)
    public Result getActivityRemark(String id){
        return Result.success(activityService.getActivityRemark(id));
    }

    @RequestMapping(value = "deleteactivityremark",method = RequestMethod.POST)
    public Result deleteActivityRemark(String id){
        return Result.success(activityService.deleteActivityRemark(id));
    }

    @RequestMapping(value = "addactivityremark",method = RequestMethod.POST)
    public Result addActivityRemark(ActivityRemark activityRemark,HttpServletRequest req){
        User user=(User)req.getSession().getAttribute("user");
        activityRemark.setCreateby(user.getName());
        return Result.success(activityService.addActivityRemark(activityRemark));
    }
    @RequestMapping(value = "updateactivityremark",method = RequestMethod.POST)
    public Result updateActivityRemark(String id,String notecontent,HttpServletRequest req){
        ActivityRemark activityRemark=new ActivityRemark();
        User user=(User)req.getSession().getAttribute("user");
        activityRemark.setEditby(user.getName());
        activityRemark.setId(id);
        activityRemark.setNotecontent(notecontent);
        return Result.success(activityService.updateActivityRemark(activityRemark));
    }
}
