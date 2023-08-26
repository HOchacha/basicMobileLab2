package org.mse.basicmobilelab2.exception;

public class UserEmailDuplicationException extends RuntimeException{
    public UserEmailDuplicationException (){ }
    public UserEmailDuplicationException(String message){
         super(message);
    }
}
