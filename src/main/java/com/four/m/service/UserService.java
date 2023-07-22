package com.four.m.service;


import com.four.m.domain.User;
import com.four.m.exception.FourException;

/*
* 描述:   UserService
* */
public interface UserService {

    User getUser();

    void register(String userName, String password) throws FourException;

    User login(String userName, String password) throws FourException;

    void updateInformation(User user) throws FourException;

    boolean checkAdminRole(User user);
}
