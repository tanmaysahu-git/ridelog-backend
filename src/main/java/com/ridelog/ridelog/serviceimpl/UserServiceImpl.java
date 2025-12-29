package com.ridelog.ridelog.serviceimpl;

import com.ridelog.ridelog.constants.ErrorCodes;
import com.ridelog.ridelog.exception.ServiceException;
import com.ridelog.ridelog.model.User;
import com.ridelog.ridelog.repositories.UserRepository;
import com.ridelog.ridelog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User createUser(User user) {
        try {
            log.info("Entering createUser : User : {}", user.getName());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            log.error("Failed to save user : ",  ex);
            // This occurs if unique constraint is violated
            throw new ServiceException(ErrorCodes.DUPLICATE_EMAIL.getCode(),
                    "Email '" + user.getEmail() + "' already exists");
        }
    }

    @Override
    public List<User> getAllUser() {
        try {
            log.info("Entering getAllUser ");
            return userRepository.findAll();
        } catch (ServiceException ex){
            log.error("Error while fetching all User");
            throw new ServiceException("Failed to fetch Users");
        }
    }

}
