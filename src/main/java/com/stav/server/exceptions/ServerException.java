package com.stav.server.exceptions;


import com.stav.server.enums.ErrorType;

public class ServerException extends Exception {
    private ErrorType errorType;

    public ServerException(ErrorType errorType, String message) {
        super(errorType.getErrorMessage() + "," + message);
        this.errorType = errorType;
    }

    public ServerException(ErrorType errorType, String message, Exception e) {
        super(errorType.getErrorMessage() + "," + message, e);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
