package com.server.MemberManagement.controller;

import com.server.MemberManagement.advice.exception.*;
import com.server.MemberManagement.response.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/userNotFound")
    public CommonResult userNotFoundException() {
        throw new UserNotFoundException();
    }

    @GetMapping(value = "/userAlreadyExists")
    public CommonResult userAlreadyExistsException() {
        throw new UserAlreadyExistsException();
    }

    @GetMapping(value = "/invalid-token")
    public CommonResult invalidToken() {
        throw new InvalidTokenException();
    }

    @GetMapping(value = "/access-token-expired")
    public CommonResult accessTokenExpiredException() {
        throw new AccessTokenExpiredException();
    }

    @GetMapping(value = "/methodArgumentNotValid")
    public CommonResult customMethodArgumentNotValidException() {
        throw new CustomMethodArgumentNotValidException();
    }

    @GetMapping(value = "/invalid-key")
    public CommonResult invalidKey() {
        throw new InvalidTokenException();
    }

    @GetMapping(value = "/token-refresh-fail")
    public CommonResult tokenRefreshFail() {
        throw new TokenRefreshFailException();
    }
}
