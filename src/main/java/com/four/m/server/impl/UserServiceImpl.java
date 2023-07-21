package com.four.m.server.impl;

import com.four.m.domain.User;
import com.four.m.mapper.UserMapper;
import com.four.m.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：     UserService实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey (1);
    }

    @Override
    public void register(String userName, String password) {
        // 查询用户名是否为空，不允许重名 
        User result = userMapper.selectByName (userName);
    }

}
