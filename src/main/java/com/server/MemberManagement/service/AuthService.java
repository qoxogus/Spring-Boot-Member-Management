package com.server.MemberManagement.service;

import com.server.MemberManagement.dto.EmailSendDto;
import com.server.MemberManagement.dto.MemberLoginRequestDto;
import com.server.MemberManagement.dto.MemberSignupRequestDto;

import java.util.Map;

public interface AuthService {
    void signUp(MemberSignupRequestDto memberSignupDto);

    Map<String, String> login(MemberLoginRequestDto loginDto);

    void sendVerificationMail(EmailSendDto emailSendDto);

    void verifyEmail(String key);
}
