package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.model.Member;

import java.util.Map;

public interface AuthService {
    void signUp(MemberSignupRequestDto memberSignupDto);

    Map<String, String> login(MemberLoginRequestDto loginDto);
}
