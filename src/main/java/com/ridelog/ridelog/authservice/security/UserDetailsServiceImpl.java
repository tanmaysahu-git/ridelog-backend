package com.ridelog.ridelog.authservice.security;

import com.ridelog.ridelog.authservice.utils.CommonUtils;
import com.ridelog.ridelog.userservice.repository.UserRepository;

import com.ridelog.ridelog.userservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        if (CommonUtils.isEmail(username)) {
            user = userRepository.findByEmail(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found"));
        } else {
            user = userRepository.findByMobileNumber(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("User not found"));
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(isEmail(username) ? user.getEmail() : user.getMobileNumber())
                .password(user.getPassword())
                .build();
    }

    private boolean isEmail(String value) {
        return value.contains("@");
    }
}
