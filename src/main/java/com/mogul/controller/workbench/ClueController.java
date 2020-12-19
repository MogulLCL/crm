package com.mogul.controller.workbench;

import com.mogul.domian.Result;
import com.mogul.pojo.Clue;
import com.mogul.pojo.User;
import com.mogul.service.ClueService;
import com.mogul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("workbench/clue")
public class ClueController {
    @Autowired
    UserService userService;
    @Autowired
    ClueService clueService;

    @RequestMapping("getuser")
    public Result getUser(){
        return Result.success(userService.getName());
    }

    @RequestMapping("save")
    public Result save(@Valid Clue clue, HttpSession httpSession){
        User user=(User)httpSession.getAttribute("user");
        clue.setCreateby(user.getName());
        return Result.success(clueService.save(clue));
    }
}
