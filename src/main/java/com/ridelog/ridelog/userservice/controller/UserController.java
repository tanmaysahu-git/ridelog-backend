package com.ridelog.ridelog.userservice.controller;

import com.ridelog.ridelog.userservice.model.User;
import com.ridelog.ridelog.userservice.dto.UserResponse;
import com.ridelog.ridelog.common.response.ApiResponse;
import com.ridelog.ridelog.common.response.ListResponse;
import com.ridelog.ridelog.userservice.service.UserService;
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

    @GetMapping("/getAllUser")
    public ResponseEntity<ListResponse<UserResponse>> getAllUser() {
        log.info("Received request to all user");
        List<User> allUser = userService.getAllUser();
        List<UserResponse> userResponses = allUser.stream()
                .map(u -> new UserResponse(u.getId(),
                        u.getName(),
                        u.getEmail(), u.getMobileNumber(),
                        u.getVehicles().stream().map(vehicle -> vehicle.getId()).toList()
                )).toList();
        log.info("Users fetched successfully");
        ListResponse<UserResponse> response = new ListResponse<>(
                userResponses,
                true,
                "All Users successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
