package com.four.m.server;


import com.four.m.domain.User;

/*
* 描述:   UserService
* */
public interface UserService {

    User getUser();

    void register(String userName, String password) ;
}
