package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;

public interface AuthService {
    void signUp(MemberSignupRequestDto memberSignupDto);

    MemberLoginRequestDto login(String id, String password);
}
