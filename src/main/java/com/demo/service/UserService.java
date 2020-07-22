package com.demo.service;

import com.demo.po.User;

public interface UserService {

    User saveUser(User user);
    User checkUser(String username, String password);
    User checkUser2(String username, String password);
}
