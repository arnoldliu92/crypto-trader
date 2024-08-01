package com.crypto.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email) {
        super(String.format("User with email %s cannot be found in database.", email));
    }
}
