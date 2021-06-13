package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;
import com.server.MemberManagement.model.Member;

public interface AuthService {
    void signUp(MemberSignupRequestDto memberSignupDto);

    Member login(String id, String password);
}
