package com.ridelog.ridelog.controller;

import com.ridelog.ridelog.model.User;
import com.ridelog.ridelog.response.ApiResponse;
import com.ridelog.ridelog.response.ListResponse;
import com.ridelog.ridelog.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // --------------------------
    // Create a new user
    // Endpoint: /api/users/createUser
    // --------------------------
    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        log.info("Received request to create user with email: {}", user.getEmail());

        User createdUser = userService.createUser(user);

        log.info("User created successfully with id: {}", createdUser.getId());

        ApiResponse<User> response = new ApiResponse<>(
                createdUser,
                true,
                "User created successfully"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<ListResponse<User>> getAllUser() {
        log.info("Received request to all user");
        List<User> allUser = userService.getAllUser();
        log.info("Users fetched successfully");
        ListResponse<User> response = new ListResponse<>(
                allUser,
                true,
                "All Users successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
