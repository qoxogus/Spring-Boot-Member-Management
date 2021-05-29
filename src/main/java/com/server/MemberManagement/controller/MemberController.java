package com.server.MemberManagement.controller;

import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MemberController {

    private final AuthService authService;

    @PostMapping("/signup")
    public MemberSignupRequestDto signup(@RequestBody MemberSignupRequestDto memberSignupRequestDto) {
        authService.signUp(memberSignupRequestDto);
        return memberSignupRequestDto;
    }
}
