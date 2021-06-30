package com.server.MemberManagement.controller;

import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import com.server.MemberManagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class CertifiedUserController {

    private final AuthService authService;
    private final ResponseService responseService;

    @DeleteMapping("/logout")
    @ResponseStatus( HttpStatus.OK )
    public CommonResult logout(HttpServletRequest request) {
        authService.logout(request);
        return responseService.getSuccessResult();
    }
}
