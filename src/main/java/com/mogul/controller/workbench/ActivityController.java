package com.mogul.controller.workbench;

import com.mogul.domian.Result;
import com.mogul.pojo.Activity;
import com.mogul.pojo.User;
import com.mogul.service.ActivityService;
import com.mogul.service.UserService;
import com.mogul.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("workbench")
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
}
