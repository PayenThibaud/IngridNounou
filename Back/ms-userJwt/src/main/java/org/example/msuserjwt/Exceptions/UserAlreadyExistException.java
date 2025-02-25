package org.example.msuserjwt.Exceptions;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(){
        super("User already exists");
    }
}
