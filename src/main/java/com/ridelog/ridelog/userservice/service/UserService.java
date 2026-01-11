package com.ridelog.ridelog.userservice.service;

import com.ridelog.ridelog.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUser();

    User getByUsername(String username);
}
