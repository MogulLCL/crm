package com.mogul.controller.workbench;

import com.mogul.domian.Result;
import com.mogul.pojo.Activity;
import com.mogul.pojo.User;
import com.mogul.service.ActivityService;
import com.mogul.service.UserService;
import com.mogul.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
    };
}
