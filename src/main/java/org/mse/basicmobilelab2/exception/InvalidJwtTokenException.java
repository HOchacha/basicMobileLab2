package org.mse.basicmobilelab2.exception;

public class InvalidJwtTokenException extends RuntimeException{
    public InvalidJwtTokenException(){}
    public InvalidJwtTokenException(String message){
        super(message);
    }
}
