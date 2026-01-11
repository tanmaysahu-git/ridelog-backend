package com.ridelog.ridelog.authservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Valid
public record RegisterRequest(

        @NotBlank
        String name,

        @Email
        String email,

        @Pattern(
                regexp = "^[6-9]\\d{9}$",
                message = "Mobile number must be a valid 10-digit Indian mobile number"
        )
        String mobileNumber,

        @NotBlank
        @Size(min = 6)
        String password
) {
}