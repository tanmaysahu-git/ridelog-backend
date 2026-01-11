package com.ridelog.ridelog.authservice.service;

import com.ridelog.ridelog.authservice.dto.*;
import com.ridelog.ridelog.userservice.model.User;

public interface AuthService {
    User registerUser(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest request);

    RefreshResponse refresh(RefreshRequest request);

    void logout(String username, String s);
}
