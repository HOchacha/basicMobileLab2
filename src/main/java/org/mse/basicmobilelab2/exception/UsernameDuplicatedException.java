package org.mse.basicmobilelab2.exception;

public class UsernameDuplicatedException extends RuntimeException{
    public UsernameDuplicatedException(){}
    public UsernameDuplicatedException(String message){
        super(message);
    }
}
