package com.mogul.service.impl;

import com.mogul.exception.LoginException;
import com.mogul.mapper.UserMapper;
import com.mogul.pojo.User;
import com.mogul.pojo.UserExample;
import com.mogul.service.UserService;
import com.mogul.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(Map<String, String> map) throws LoginException{
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginactEqualTo(map.get("loginact")).andLoginpwdEqualTo(map.get("loginpwd"));
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList.size() == 0)
             throw new LoginException("用户名或密码错误！");
        if("0".equals(userList.get(0).getLockstate()))
            throw  new LoginException("账号已被锁定！");
        if(!"".equals(userList.get(0).getExpiretime())&DateTimeUtil.getSysTime().compareTo(userList.get(0).getExpiretime())>0)
            throw  new LoginException("账号已失效！");
        if(!"".equals(userList.get(0).getAllowips())&!userList.get(0).getAllowips().contains(map.get("ip")))
            throw  new LoginException("账号登录ip受限！");
        return userList.get(0);
    }

    @Override
    public List<User> getName() {
        return userMapper.selectByName();
    }

}
