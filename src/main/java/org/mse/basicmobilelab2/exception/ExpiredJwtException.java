package org.mse.basicmobilelab2.exception;

public class ExpiredJwtException extends RuntimeException{
    public ExpiredJwtException(){}
    public ExpiredJwtException(String message){
        super(message);
    }
}
