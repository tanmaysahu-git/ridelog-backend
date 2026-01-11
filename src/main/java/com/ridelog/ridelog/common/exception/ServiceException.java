package com.ridelog.ridelog.common.exception;

import com.ridelog.ridelog.common.constants.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private final String errorCode;

    public ServiceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCodes errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
}

