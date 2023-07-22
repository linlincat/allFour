package com.four.m.service.impl;

import com.four.m.domain.User;
import com.four.m.exception.FourException;
import com.four.m.exception.FourExceptionEnum;
import com.four.m.mapper.UserMapper;
import com.four.m.service.UserService;
import com.four.m.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

/**
 * 描述：     UserService实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey (1);
    }

    //1、检查是否正确的重写了父类中的方法。 2、标明代码，这是一个重写的方法。
    @Override
    public void register(String userName, String password) throws FourException {
        // 查询用户名是否为空，不允许重名 
        User result = userMapper.selectByName (userName);
        if (result != null) {
            throw new FourException (FourExceptionEnum.NAME_EXISTED);
        }
        // 写入数据库
        User user = new User ();
        user.setUsername (userName);
        try {
            user.setPassword (MD5Utils.getMD5Str (password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace ();
        }
        // 将创建出来的用户类，用mapper去插入到数据库
        int count = userMapper.insertSelective (user);
        if(count == 0 ) {
            throw new FourException (FourExceptionEnum.INSERT_FAILED);
        }
    }

}
