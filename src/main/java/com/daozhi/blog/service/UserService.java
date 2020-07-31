package com.daozhi.blog.service;

import com.daozhi.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
