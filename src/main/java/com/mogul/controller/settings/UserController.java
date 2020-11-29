package com.mogul.controller.settings;

import com.mogul.domian.Result;
import com.mogul.exception.LoginException;
import com.mogul.pojo.User;
import com.mogul.service.UserService;
import com.mogul.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("settings/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login(String loginact, String loginpwd, HttpServletRequest request) throws LoginException {
        Map<String,String> map=new HashMap<>();
        map.put("loginact",loginact);
        map.put("loginpwd", MD5Util.getMD5(loginpwd));
        map.put("ip",request.getRemoteAddr());
        User user=userService.login(map);
        request.getSession().setAttribute("user",user);
        return Result.success(user);
    }

}
