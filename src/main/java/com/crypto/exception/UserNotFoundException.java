package com.crypto.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId) {
        super(String.format("User %d cannot be found in database.", userId));
    }
}
