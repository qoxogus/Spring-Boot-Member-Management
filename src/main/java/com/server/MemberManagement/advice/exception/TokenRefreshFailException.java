package com.server.MemberManagement.advice.exception;

public class TokenRefreshFailException extends RuntimeException{
    public TokenRefreshFailException(String msg, Throwable t){
        super(msg, t);
    }
    public TokenRefreshFailException(String msg){
        super(msg);
    }
    public TokenRefreshFailException(){
        super();
    }
}
