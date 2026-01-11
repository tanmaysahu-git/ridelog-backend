package com.ridelog.ridelog.userservice.impl;

import com.ridelog.ridelog.authservice.utils.CommonUtils;
import com.ridelog.ridelog.common.exception.ServiceException;
import com.ridelog.ridelog.userservice.model.User;
import com.ridelog.ridelog.userservice.repository.UserRepository;
import com.ridelog.ridelog.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUser() {
        try {
            log.info("Entering getAllUser ");
            return userRepository.findAll();
        } catch (ServiceException ex) {
            log.error("Error while fetching all User");
            throw new ServiceException("Failed to fetch Users");
        }
    }

    @Override
    public User getByUsername(String username) {
        try {
            log.info("Entering getByUserName");
            User user = new User();
            if (CommonUtils.isEmail(username)) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found"));
            } else {
                return userRepository.findByMobileNumber(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found"));
            }
        } catch (ServiceException ex) {
            log.error("Error while fetching User : ", username);
            throw new ServiceException("Failed to fetch user");
        }

    }

}
