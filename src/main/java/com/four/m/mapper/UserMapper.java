package com.four.m.mapper;

import com.four.m.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByName(String userName);

    // mapper里面如果有一个参数是不需要添加param注解的
    User selectLogin(@Param("userName") String userName, @Param("password") String password);
}