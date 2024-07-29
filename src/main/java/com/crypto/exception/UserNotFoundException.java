package com.crypto.exception;

public class UserNotFoundException extends RuntimeException{
    UserNotFoundException(Long id) {
        super(String.format("User %l cannot be found in database.", id));
    }
}
