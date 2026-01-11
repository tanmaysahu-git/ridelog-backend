package com.ridelog.ridelog.authservice.impl;

import com.ridelog.ridelog.authservice.dto.*;
import com.ridelog.ridelog.authservice.model.RefreshToken;
import com.ridelog.ridelog.authservice.repository.RefreshTokenRepository;
import com.ridelog.ridelog.authservice.security.JwtService;
import com.ridelog.ridelog.authservice.security.UserDetailsServiceImpl;
import com.ridelog.ridelog.common.constants.ErrorCodes;
import com.ridelog.ridelog.userservice.model.User;
import com.ridelog.ridelog.common.exception.ServiceException;
import com.ridelog.ridelog.userservice.repository.UserRepository;
import com.ridelog.ridelog.authservice.service.AuthService;
import com.ridelog.ridelog.userservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;
    //    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    @Value("${jwt.refresh-token-expiry}")
    private long refreshTokenExpiry;

    @Override
    @Transactional
    public User registerUser(RegisterRequest registerRequest) {
        User user = new User();
        try {
            log.info("Entering registerUser : User : {}", registerRequest.name());
            user.setEmail(registerRequest.email());
            user.setName(registerRequest.name());
            user.setMobileNumber(registerRequest.mobileNumber());
            user.setPassword(passwordEncoder.encode(registerRequest.password()));
            return userRepository.save(user);
        } catch (ServiceException ex) {
            log.error("Failed to save user : ", ex);
            // This occurs if unique constraint is violated
            throw new ServiceException("Exception occurred while registering user");
        }
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.userName(),
                                request.password()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        User user =
                userService.getByUsername(userDetails.getUsername());

        String accessToken =
                jwtService.generateAccessToken(userDetails);

        String refreshToken =
                jwtService.generateRefreshToken(userDetails);

        saveRefreshtoken(user, refreshToken);

        return new LoginResponse(
                userDetails.getUsername(),
                accessToken,
                refreshToken
        );
    }


    @Override
    @Transactional
    public RefreshResponse refresh(RefreshRequest request) {

        RefreshToken stored =
                validateRefreshToken(request.refreshToken());

        User user = stored.getUser();

        // Rotate refresh token
        revokeRefreshToken(stored);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        Strings.isNotBlank(user.getEmail()) ? user.getEmail() : user.getMobileNumber()
                );

        String newAccessToken =
                jwtService.generateAccessToken(userDetails);

        String newRefreshToken =
                jwtService.generateRefreshToken(userDetails);

        saveRefreshtoken(user, newRefreshToken);

        return new RefreshResponse(newAccessToken, newRefreshToken);
    }

    @Override
    @Transactional
    public void logout(String username, String refreshToken) {

        RefreshToken stored =
                validateRefreshToken(refreshToken);

        if (!(stored.getUser().getEmail().equals(username) || stored.getUser().getMobileNumber().equals(username))) {
            throw new ServiceException(
                    ErrorCodes.UNAUTHORIZED_ACTION
            );
        }
        revokeRefreshToken(stored);
    }

    public RefreshToken saveRefreshtoken(User user, String token) {
        RefreshToken refresh = new RefreshToken();
        refresh.setUser(user);
        refresh.setToken(token);
        refresh.setExpiryDate(
                Instant.now().plusMillis(refreshTokenExpiry)
        );
        return refreshTokenRepository.save(refresh);
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refresh = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ServiceException(ErrorCodes.INVALID_REFRESH_CODE));

        if (refresh.isRevoked()) {
            throw new ServiceException(ErrorCodes.REFRESH_TOKEN_REVOKED);
        }

        if (refresh.getExpiryDate().isBefore(Instant.now())) {
            throw new ServiceException(ErrorCodes.REFRESH_TOKEN_EXPIRED);
        }

        return refresh;
    }

    public void revokeRefreshToken(RefreshToken token) {
        refreshTokenRepository.delete(token);
        // can be done if we need to keep logs of login but for now we delete to keep db clean
//        token.setRevoked(true);
//        refreshTokenRepository.save(token);
    }
}
