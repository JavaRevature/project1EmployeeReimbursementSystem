package com.revature.ers_backend.exceptions;

public class DuplicatedUsersException extends RuntimeException{
    // Constructor with a custom message
    public DuplicatedUsersException(String message) {
        super(message);
    }
}
