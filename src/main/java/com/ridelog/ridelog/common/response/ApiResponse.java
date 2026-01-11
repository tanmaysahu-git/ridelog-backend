package com.ridelog.ridelog.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private long timestamp;

    public ApiResponse(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}

