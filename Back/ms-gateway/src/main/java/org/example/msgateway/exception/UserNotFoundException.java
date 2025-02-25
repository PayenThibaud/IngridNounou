package org.example.msgateway.exception;


public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("User Not found");
    }
}