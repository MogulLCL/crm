package com.mogul.exception;

import com.mogul.domian.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DefaultException {
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exceptions(Exception e){
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Result loginException(LoginException e){
        return Result.error(e.getMessage(),"");
    }
}
