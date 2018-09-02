package com.education.exception;

public class NoResultFoundException extends RuntimeException {
    public NoResultFoundException() {
    }

    public NoResultFoundException(String message) {
        super(message);
    }

    public NoResultFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
