package com.server.MemberManagement.advice.exception;

public class InvalidKeyException extends RuntimeException{
    public InvalidKeyException(String msg, Throwable t){
        super(msg, t);
    }
    public InvalidKeyException(String msg){
        super(msg);
    }
    public InvalidKeyException(){
        super();
    }
}
