package com.crypto.exception;

public class InvalidInputException extends IllegalArgumentException {
    public InvalidInputException(Enum<?> type) {
        super(String.format("Invalid %s has been entered for %s", type.toString(), type.getDeclaringClass()));
    }
}
