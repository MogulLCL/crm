package com.mogul.exception;

import com.mogul.domian.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
public class DefaultException {
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exceptions(Exception e){
        e.printStackTrace();
        return Result.error("");
    }

    @ExceptionHandler({BindException.class,MethodArgumentNotValidException.class})
    @ResponseBody
    public Result methodArgumentNotValidException(BindException ex){
        return Result.error(ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Result loginException(LoginException e){
        return Result.error(e.getMessage(),"");
    }

    @ExceptionHandler(OtherExcption.class)
    @ResponseBody
    public Result OtherException(OtherExcption e){
        return Result.error(e.getMessage(),"");
    }
}
