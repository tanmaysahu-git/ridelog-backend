package com.ridelog.ridelog.constants;

public enum ErrorCodes {
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

