package com.crypto.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId) {
        super(String.format("User %l cannot be found in database.", userId));
    }
}
