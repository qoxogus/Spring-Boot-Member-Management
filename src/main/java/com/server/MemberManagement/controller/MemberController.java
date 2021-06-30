package com.server.MemberManagement.controller;

import com.server.MemberManagement.dto.EmailSendDto;
import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import com.server.MemberManagement.response.SingleResult;
import com.server.MemberManagement.security.JwtTokenProvider;
import com.server.MemberManagement.service.AuthService;
import com.server.MemberManagement.service.EmailService;
import com.server.MemberManagement.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.server.MemberManagement.service"})
public class MemberController {

    private final AuthService authService;
    private final ResponseService responseService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResult signup(@RequestBody MemberSignupRequestDto memberSignupRequestDto) {
        authService.signUp(memberSignupRequestDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public SingleResult<Map<String, String>>  login(@Valid @RequestBody MemberLoginRequestDto memberLoginRequestDto) throws Exception {
        Map<String, String> loginResult = authService.login(memberLoginRequestDto);
        return responseService.getSingleResult(loginResult);
    }

    @PostMapping("/email")
    public CommonResult email(@RequestBody EmailSendDto emailSendDto) {
        authService.sendVerificationMail(emailSendDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/verify/email")
    public CommonResult verifyEmail(@RequestBody String key) {
        authService.verifyEmail(key);
        return responseService.getSuccessResult();
    }
}
