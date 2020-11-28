package com.mogul.service;

import com.mogul.exception.LoginException;
import com.mogul.pojo.User;

import java.util.Map;

public interface UserService {
    User login(Map<String,String> map) throws LoginException;
}
