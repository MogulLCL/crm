package com.mogul.service;

import com.mogul.exception.LoginException;
import com.mogul.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User login(Map<String,String> map) throws LoginException;
    List<User> getName();
}
