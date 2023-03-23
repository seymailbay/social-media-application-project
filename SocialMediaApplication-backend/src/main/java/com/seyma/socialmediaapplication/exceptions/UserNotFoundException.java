package com.seyma.socialmediaapplication.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){
        super();
    }
    public UserNotFoundException(String message){
        super(message);
    }
}
