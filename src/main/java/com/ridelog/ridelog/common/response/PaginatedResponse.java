package com.ridelog.ridelog.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedResponse<T> {
    private boolean success;
    private String message;
    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private long timestamp;

    public PaginatedResponse(List<T> data, int page, int size, long totalElements, int totalPages, String message) {
        this.success = true;
        this.data = data;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}

