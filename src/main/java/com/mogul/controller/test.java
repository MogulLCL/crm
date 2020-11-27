package com.mogul.controller;

import com.mogul.domian.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @RequestMapping("test")
    public Result test(String m){
        return Result.success(m);
    }
}
