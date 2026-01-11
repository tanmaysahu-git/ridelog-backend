package com.ridelog.ridelog.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActionResponse {
    private boolean success;
    private String message;
    private long timestamp;

    public ActionResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
