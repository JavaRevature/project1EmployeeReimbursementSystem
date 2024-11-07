package com.revature.ers_backend.exceptions;

public class NoUsersFoundException extends RuntimeException {

    public NoUsersFoundException(String message) {
        super(message);
    }

}
