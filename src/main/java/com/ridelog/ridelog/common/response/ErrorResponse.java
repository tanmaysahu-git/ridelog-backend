package com.ridelog.ridelog.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String error;
    private Map<String, String> details;
    private long timestamp;

    public ErrorResponse(String error, Map<String, String> details) {
        this.success = false;
        this.error = error;
        this.details = details;
        this.timestamp = System.currentTimeMillis();
    }
}
