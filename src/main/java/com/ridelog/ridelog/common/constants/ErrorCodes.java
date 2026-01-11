package com.ridelog.ridelog.common.constants;

public enum ErrorCodes {
    INVALID_REFRESH_CODE("AUTH_001", "Invalid refresh token"),
    UNAUTHORIZED_ACTION("AUTH_002", "Unauthorised action"),
    REFRESH_TOKEN_REVOKED("AUTH_003", "Refresh token revoked"),
    REFRESH_TOKEN_EXPIRED("AUTH_004", "Refresh token expired"),

    USER_NOT_FOUND("USR_001", "User not found"),
    DUPLICATE_EMAIL("USR_002", "Email already exists"),

    VEHICLE_NOT_FOUND("VEH_001", "Vehicle not found"),
    SERVICE_RECORD_NOT_FOUND("SRV_001", "Service record not found");

    private final String code;
    private final String defaultMessage;

    ErrorCodes(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}

