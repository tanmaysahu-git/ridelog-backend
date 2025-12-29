package com.ridelog.ridelog.service;

import com.ridelog.ridelog.model.User;

import java.util.List;

public interface UserService {
    public User createUser(User user);

    List<User> getAllUser();
}
