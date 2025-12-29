package com.ridelog.ridelog.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ListResponse<T> {
    private boolean success;
    private String message;
    private List<T> data;
    private int count;
    private long timestamp;

    public ListResponse(List<T> data, boolean success, String message) {
        this.data = data;
        this.count = data != null ? data.size() : 0;
        this.success = success;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
