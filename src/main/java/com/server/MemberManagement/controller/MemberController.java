package com.server.MemberManagement.controller;

import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.model.Member;
import com.server.MemberManagement.response.CommonResult;
import com.server.MemberManagement.response.ResponseService;
import com.server.MemberManagement.response.SingleResult;
import com.server.MemberManagement.security.JwtTokenProvider;
import com.server.MemberManagement.service.AuthService;
import com.server.MemberManagement.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
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
    private final RedisUtil redisUtil;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public CommonResult signup(@RequestBody MemberSignupRequestDto memberSignupRequestDto) {
        authService.signUp(memberSignupRequestDto);
        return responseService.getSuccessResult();
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody MemberLoginRequestDto memberLoginRequestDto) throws Exception {
        final Member member = authService.login(memberLoginRequestDto.getUsername(), memberLoginRequestDto.getPassword());



        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }

    @GetMapping("/user")
    public String certifiedUser() {
        return "Hi~ Certified You!!!";
    }
}
